package space.zyzy.dubhe.concurrent.thread;

/**
 * Thread类join方法测试
 * API对其解释为：等待该线程终止。
 * A线程调用B线程join方法,则A被阻塞知道B执行完毕
 */
public class RunThreadJoinTest implements Runnable {

    private static int k;

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            k++;
        }
        System.out.println(Thread.currentThread().getName() + " is working");
    }

    public static void main(String[] args) throws InterruptedException {

        // 当前是main线程执行
        System.out.println(Thread.currentThread().getName() + " is working");

        // 启动线程执行
        Thread t = new Thread(new RunThreadJoinTest());
        t.start();

        /*
         * 打印的key值时不确定的
         * 因为我们新启动的线程和main线程在同时执行
         * 所以这时可以将t线程join到当前线程中来,也就是API中所说的,等待该线程终止(执行完毕)
         * 当然还可以指定等待时间
         */

        // 添加此方法后,main将等待t执行完毕
        t.join();

        // 打印k的值
        System.out.println(k);
    }
}
