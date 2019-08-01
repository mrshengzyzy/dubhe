package space.zyzy.dubhe.aop.dynamicproxy;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * 复制的源码java.lang.reflect.WeakCache
 * 因为代理类的创建比较耗时,因此引入了缓存机制提高性能
 * 采用了二级缓存的方式,一级缓存通过classLoader产生,二级缓存由代理的所有接口产生(相关分析在Proxy类中)
 *
 * @param <K> 缓存key
 * @param <P> 生成二级缓存的key需要的参数
 * @param <V> 二级缓存value
 */

final class WeakCache<K, P, V> {

    // 引用队列(跟GC有关,暂不分析)
    private final ReferenceQueue<K> refQueue = new ReferenceQueue<>();

    // the key type is Object for supporting null key(key设置为Object类型是为了支持null key)
    // 缓存, key为一级缓存, value为二级缓存
    // 二级缓存的意思就是需要使用二级key获取相应的二级value
    private final ConcurrentMap<Object, ConcurrentMap<Object, Supplier<V>>> map = new ConcurrentHashMap<>();

    // 为了实现缓存的过期机制
    private final ConcurrentMap<Supplier<V>, Boolean> reverseMap = new ConcurrentHashMap<>();

    // 生成二级缓存key的工厂
    private final BiFunction<K, P, ?> subKeyFactory;

    // 生成二级缓存value的工厂
    private final BiFunction<K, P, V> valueFactory;

    /**
     * 构造函数
     * 注意这里初始化了 subKeyFactory 和 valueFactory,也就是说相关逻辑是由调用方实现
     */
    public WeakCache(BiFunction<K, P, ?> subKeyFactory, BiFunction<K, P, V> valueFactory) {
        this.subKeyFactory = Objects.requireNonNull(subKeyFactory);
        this.valueFactory = Objects.requireNonNull(valueFactory);
    }

    /**
     * 可以发现本类并没有put方法而是只有get方法
     * 原因就是get方法可以执行push的操作
     * 没有get到值时可以放置在缓存map中,可以get到值时直接返回值
     * 注意这里传入的是一级key和二级key参数(用来计算二级key)
     * 返回的是二级value
     */
    public V get(K key, P parameter) {

        // 清空无效缓存,不分析
        Objects.requireNonNull(parameter);
        expungeStaleEntries();

        // 获取缓存的key
        Object cacheKey = CacheKey.valueOf(key, refQueue);

        // 获取对应的二级缓存
        ConcurrentMap<Object, Supplier<V>> valuesMap = map.get(cacheKey);

        // 第一次肯定是无法取到的
        if (valuesMap == null) {

            // 向缓存中添加一个新的二级缓存
            ConcurrentMap<Object, Supplier<V>> oldValuesMap = map.putIfAbsent(cacheKey, valuesMap = new ConcurrentHashMap<>());
            if (oldValuesMap != null) {
                valuesMap = oldValuesMap;
            }
        }

        // 计算二级缓存key,很显然这是由调用方决定的
        Object subKey = Objects.requireNonNull(subKeyFactory.apply(key, parameter));

        // 获取二级缓存value,很显然第一次得到的是null(71行添加了空的二级缓存)
        Supplier<V> supplier = valuesMap.get(subKey);
        Factory factory = null;

        // 看到这个就知道是为了线程安全
        while (true) {

            // 第一次获取第二次循supplier将不是null
            if (supplier != null) {
                // supplier might be a Factory or a CacheValue<V> instance
                // 为什么说可能是两种类型呢,第一次获取的时候是Factory对象
                // 第二次获取就是CacheValue对象了(234行替换)
                // 由于CacheValue是一个WeakReference,所以持有的对象可能被回收,
                // 一旦被回收,那么就按照null继续处理,也就是101行说的cache中没有值了(被回收了)
                V value = supplier.get();
                if (value != null) {
                    return value;
                }
            }

            // else no supplier in cache
            // or a supplier that returned null (could be a cleared CacheValue
            // or a Factory that wasn't successful in installing the CacheValue)

            // 第一次循环factory很显然是null,执行初始化操作
            if (factory == null) {
                factory = new Factory(key, parameter, subKey, valuesMap);
            }

            // 第一次获取第一次循环走这个逻辑,说明根据二级key没有对应的二级value
            if (supplier == null) {

                // 那么就将factory对象作为value构造二级缓存
                supplier = valuesMap.putIfAbsent(subKey, factory);

                // 如果执行put后返回null,表示他的原始value确实是null,没有被其他线程趁机put过
                if (supplier == null) {
                    // successfully installed Factory
                    supplier = factory;
                }
                // else retry with winning supplier
                // 到这里还没有返回值呢,所以继续循环取真正的二级缓存
            } else {
                // 第>1次获取会走这里或者其他线程同步修改了值
                // 尝试使用新值替换
                if (valuesMap.replace(subKey, supplier, factory)) {
                    // successfully replaced
                    // cleared CacheEntry / unsuccessful Factory
                    // with our Factory
                    supplier = factory;
                } else {
                    // retry with current supplier
                    // 否则就使用原始值
                    supplier = valuesMap.get(subKey);
                }
            }
        }
    }

    /**
     * 不做分析
     */
    public boolean containsValue(V value) {
        Objects.requireNonNull(value);

        expungeStaleEntries();
        return reverseMap.containsKey(new LookupValue<>(value));
    }

    /**
     * 不做分析
     */
    public int size() {
        expungeStaleEntries();
        return reverseMap.size();
    }

    /**
     * 删除无效引用,不做分析
     */
    private void expungeStaleEntries() {
        CacheKey<K> cacheKey;
        while ((cacheKey = (CacheKey<K>) refQueue.poll()) != null) {
            cacheKey.expungeFrom(map, reverseMap);
        }
    }

    /**
     * 一个实现了Supplier接口的类
     * 好处就是可以在get的时候懒加载返回数据
     */
    private final class Factory implements Supplier<V> {

        // 缓存key
        private final K key;

        // 计算二级缓存key需要的参数
        private final P parameter;

        // 由parameter计算得来的二级缓存key
        private final Object subKey;

        // 二级缓存
        private final ConcurrentMap<Object, Supplier<V>> valuesMap;

        Factory(K key, P parameter, Object subKey,
                ConcurrentMap<Object, Supplier<V>> valuesMap) {
            this.key = key;
            this.parameter = parameter;
            this.subKey = subKey;
            this.valuesMap = valuesMap;
        }

        /**
         * !!!!!!!!!!!!!!!!
         * 这是一个同步方法
         * !!!!!!!!!!!!!!!!
         */
        @Override
        public synchronized V get() { // serialize access
            // re-check
            // 再一次去二级缓存里面获取Supplier, 用来验证是否是Factory本身
            Supplier<V> supplier = valuesMap.get(subKey);
            if (supplier != this) {
                // something changed while we were waiting:
                // might be that we were replaced by a CacheValue
                // or were removed because of failure ->
                // return null to signal WeakCache.get() to retry the loop
                // 在这里验证supplier是否是Factory实例本身, 如果不则返回null让调用者继续轮询重试
                // 期间supplier可能替换成了CacheValue, 或者由于生成代理类失败被从二级缓存中移除了
                return null;
            }
            // else still us (supplier == this)

            // create new value
            V value = null;
            try {
                // 二级缓存value也是由调用方确定的
                value = Objects.requireNonNull(valueFactory.apply(key, parameter));
            } finally {
                // remove us on failure
                if (value == null) {
                    valuesMap.remove(subKey, this);
                }
            }
            // the only path to reach here is with non-null value
            assert value != null;

            // wrap value with CacheValue (WeakReference)
            // 将二级value使用弱引用包装
            CacheValue<V> cacheValue = new CacheValue<>(value);

            // try replacing us with CacheValue (this should always succeed)
            // 将包装后的cacheValue放入二级缓存中,这个操作没理由失败
            // 注意这里二级缓存有了CacheValue类型对象
            if (valuesMap.replace(subKey, this, cacheValue)) {
                // put also in reverseMap
                // 标记二级缓存
                reverseMap.put(cacheValue, Boolean.TRUE);
            } else {
                throw new AssertionError("Should not reach here");
            }

            // 注意返回的是真实value(而不是被弱引用包装后的value)
            return value;
        }
    }

    // =============================================== 下面的方法都是给GC使用的,暂不讨论 ============================= //

    /**
     * Common type of value suppliers that are holding a referent.
     * The {@link #equals} and {@link #hashCode} of implementations is defined
     * to compare the referent by identity.
     */
    private interface Value<V> extends Supplier<V> {
    }

    /**
     * An optimized {@link Value} used to look-up the value in
     * {@link WeakCache#containsValue} method so that we are not
     * constructing the whole {@link CacheValue} just to look-up the referent.
     */
    private static final class LookupValue<V> implements Value<V> {
        private final V value;

        LookupValue(V value) {
            this.value = value;
        }

        @Override
        public V get() {
            return value;
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(value); // compare by identity
        }

        @Override
        public boolean equals(Object obj) {
            return obj == this ||
                    obj instanceof Value &&
                            this.value == ((Value<?>) obj).get();  // compare by identity
        }
    }

    /**
     * A {@link Value} that weakly references the referent.
     */
    private static final class CacheValue<V> extends WeakReference<V> implements Value<V> {
        private final int hash;

        CacheValue(V value) {
            super(value);
            this.hash = System.identityHashCode(value); // compare by identity
        }

        @Override
        public int hashCode() {
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            V value;
            return obj == this ||
                    obj instanceof Value &&
                            // cleared CacheValue is only equal to itself
                            (value = get()) != null &&
                            value == ((Value<?>) obj).get(); // compare by identity
        }
    }

    /**
     * CacheKey containing a weakly referenced {@code key}. It registers
     * itself with the {@code refQueue} so that it can be used to expunge
     * the entry when the {@link WeakReference} is cleared.
     */
    private static final class CacheKey<K> extends WeakReference<K> {

        // a replacement for null keys
        private static final Object NULL_KEY = new Object();

        static <K> Object valueOf(K key, ReferenceQueue<K> refQueue) {
            return key == null
                    // null key means we can't weakly reference it,
                    // so we use a NULL_KEY singleton as cache key
                    ? NULL_KEY
                    // non-null key requires wrapping with a WeakReference
                    : new CacheKey<>(key, refQueue);
        }

        private final int hash;

        private CacheKey(K key, ReferenceQueue<K> refQueue) {
            super(key, refQueue);
            this.hash = System.identityHashCode(key);  // compare by identity
        }

        @Override
        public int hashCode() {
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            K key;
            return obj == this ||
                    obj != null &&
                            obj.getClass() == this.getClass() &&
                            // cleared CacheKey is only equal to itself
                            (key = this.get()) != null &&
                            // compare key by identity
                            key == ((CacheKey<K>) obj).get();
        }

        void expungeFrom(ConcurrentMap<?, ? extends ConcurrentMap<?, ?>> map,
                         ConcurrentMap<?, Boolean> reverseMap) {
            // removing just by key is always safe here because after a CacheKey
            // is cleared and enqueue-ed it is only equal to itself
            // (see equals method)...
            ConcurrentMap<?, ?> valuesMap = map.remove(this);
            // remove also from reverseMap if needed
            if (valuesMap != null) {
                for (Object cacheValue : valuesMap.values()) {
                    reverseMap.remove(cacheValue);
                }
            }
        }
    }
}
