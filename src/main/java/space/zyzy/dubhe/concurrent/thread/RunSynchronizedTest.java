package space.zyzy.dubhe.concurrent.thread;

/**
 * 使用javap -verbose 查看字节码
 * 注意使用关键字 synchronized 修饰实例方法、静态方法、代码块时
 * JVM 对其class字节码的不同处理
 */
public class RunSynchronizedTest {

    /**
     * 修饰静态方法
     * 锁对象是当前类的Class对象,就是 RunSynchronizedTest.class 对象
     * Method Access flag 添加了标识: ACC_SYNCHRONIZED
     */
    public synchronized static void staticIncrease() {

    }

    /**
     * 修饰实例方法
     * 锁对象是实例对象
     * Method Access flag 添加了标识: ACC_SYNCHRONIZED
     */
    public synchronized void increase() {

    }

    /**
     * 修饰代码块
     * 无Access flag标识,使用了字节码指令 monitorenter & monitorexit
     */
    public void lockObject() {
        synchronized (this) {
        }
    }
}

