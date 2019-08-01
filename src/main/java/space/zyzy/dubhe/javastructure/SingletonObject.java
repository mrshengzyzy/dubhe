package space.zyzy.dubhe.javastructure;

/**
 * 单例模式的写法：
 * http://wuchong.me/blog/2014/08/28/how-to-correctly-write-singleton-pattern/
 * 饿汉、懒汉、双重检验锁、静态内部类、枚举
 */
public class SingletonObject {

    /**
     * 饿汉式
     * 优点：写法简单,由JVM保证线程安全
     * 缺点：非延迟加载,且加载类时就初始化占用内存,若初始化速度较慢时影响启动性能
     */
    static class Singleton {

        // 静态字段在类初始化阶段被执行,有类加载机制保证了线程安全
        private static Singleton ourInstance = new Singleton();

        // 私有构造方法
        private Singleton() {
        }

        public static Singleton getInstance() {
            return ourInstance;
        }
    }

    /**
     * 懒汉式
     * 优点：懒加载，系统资源占用少
     * 缺点：不高效。任何时候只能有一个线程调用 getInstance() 方法。但是同步操作只需要在第一次调用时才被需要。
     */
    static class SingletonOne {

        // 静态字段,未初始化
        private static SingletonOne instance;

        // 私有构造方法
        private SingletonOne() {
        }

        // 使用synchronized 关键字保证线程安全
        public static synchronized SingletonOne getInstance() {
            if (instance == null) {
                instance = new SingletonOne();
            }
            return instance;
        }
    }

    /**
     * 双重检验锁(DLC double checked locking pattern)
     * 优点：锁粒度降低提高了效率
     * 缺点：写法复杂
     */
    static class SingletonTwo {

        // 静态字段必须由 volatile 修饰
        private static volatile SingletonTwo instance;

        // 私有构造方法
        private SingletonTwo() {
        }

        /**
         * 失效原因分析：
         * 语句④不是一个原子操作，在JVM中至少做了下面三件事：
         * 1、给 instance 分配内存
         * 2、调用 Singleton 的构造函数来初始化成员变量
         * 3、将instance对象指向分配的内存空间（执行完这步instance 就为非null了）
         * 但是 JVM 会进行指令重排序的优化，就是说最终的执行顺序可能是 1-2-3 也可能是 1-3-2
         * 如果是后者，则在 3 执行完毕、2 未执行之前，线程二抢占了CPU，在①处判断 instance 非 null 了（但却没有初始化），直接返回报错。
         * 所以需要将instance字段改为由volatile修饰。
         * volatile能够禁止指令重排序，确切的说事根据happens-before原则，就是对于一个volatile 变量的写操作都先行发生于后面对这个变量的读操作。
         * 这个的实现机制是在 volatile 变量的赋值操作后面会有一个内存屏障（Memory Barrier），读操作不会被重排序到内存屏障之前。
         * 也就是说，无论执行顺序是 132 还是 123 ，只要线程二想读instance的值，那就必须等待语句④完全执行完毕（因为语句④是对instance的写操作）
         */
        public static SingletonTwo getSingleton() {
            if (instance == null) {                     // ①
                synchronized (Singleton.class) {        // ②
                    if (instance == null) {             // ③
                        instance = new SingletonTwo();  // ④
                    }
                }
            }
            return instance;
        }
    }

    /**
     * 静态内部类
     * 使用了 Initialization Demand Holder (IoDH) 技术
     * 类级内部类只有在第一次被使用的时候才被会装载,而JVM加载类并初始化static字段是线程安全的
     */
    static class SingletonThree {

        // 私有内部类
        private static class SingletonHolder {
            private static final SingletonThree INSTANCE = new SingletonThree();
        }

        // 构造方法私有
        private SingletonThree() {
        }

        // 无需锁同步
        public static final SingletonThree getInstance() {
            return SingletonHolder.INSTANCE;
        }
    }

    /**
     * 枚举
     * 有JVM保证线程安全,还能防止反序列化导致重新创建新的对象
     */
    enum SingletonFour {
        INSTANCE
    }
}

