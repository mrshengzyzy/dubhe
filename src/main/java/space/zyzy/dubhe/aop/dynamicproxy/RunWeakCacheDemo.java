package space.zyzy.dubhe.aop.dynamicproxy;

import java.util.function.BiFunction;

/**
 * WeakCache示例
 * 仅用来演示缓存效果
 */
public class RunWeakCacheDemo {

    public static void main(String[] args) {

        String key = "hello";
        String value = " world";

        /*
         * 打印结果：
         * value类被执行
         * value:hello world
         * ========================
         * value:hello world
         * 可以看出来第一次真正执行初始化过程,第二次直接从缓存中获取
         */
        WeakCache<String, String, String> weakCache = new WeakCache<>(new Key(), new Value());
//        System.out.println(weakCache.get(key, value));
//        System.out.println(weakCache.get(key, value));

        System.out.println("=========================");

        /*
         * 打印结果：
         * value类被执行
         * value:hello world
         * value类被执行
         * value:hello world
         * 第一次获取完毕后本来有缓存了,但因为value是弱引用,强制GC后被回收
         * 第二次获取重新实例化
         */
        System.out.println(weakCache.get(key, value));
        System.gc();
        System.out.println(weakCache.get(key, value));
    }
}


class Key implements BiFunction<String, String, String> {

    @Override
    public String apply(String s, String s2) {
        return "key:" + s + s2;
    }
}

class Value implements BiFunction<String, String, String> {

    @Override
    public String apply(String s, String s2) {
        System.out.println("value实例化");
        return "value:" + s + s2;
    }
}