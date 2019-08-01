package space.zyzy.dubhe.concurrent.thread;

import java.util.concurrent.TimeUnit;

/**
 * public static boolean interrupted()
 * 静态方法,返回当前线程中断状态并清除中断状态(GetAndReset),由于清除了中断状态,因此第二次调用返回false
 * public boolean isInterrupted()
 * 实例方法,检测当前线程中断状态
 * public void interrupt()
 * 实例方法,中断线程(设置了中断状态的值)
 */
public class RunThreadInterruptTest extends Thread {

    @Override
    public void run() {

        // 当前线程属性
        Thread currentThread = Thread.currentThread();
        String name = currentThread.getName();
        System.out.println(name + "开始执行");

        try {
            System.out.println(name + "准备休眠5秒");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            System.out.println(name + "被中断提前结束休眠,设置中断");
            currentThread.interrupt();
        }

        // 检查中断状态
        System.out.println(name + "首次检查中断状态:" + currentThread.isInterrupted());

        // 返回中断状态并重置中断状态
        System.out.println(name + "使用interrupted重置中断状态:" + Thread.interrupted());

        // 再次检查中断状态
        System.out.println(name + "再次检查中断状态:" + currentThread.isInterrupted());

        // 不断检测中断状态
        do {
            System.out.println(currentThread.getName() + "运行中");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                currentThread.interrupt();
            }
        } while (!currentThread.isInterrupted());

        // 检测到中断结束while处理
        System.out.println(currentThread.getName() + "被中断");
    }

    /**
     * Thread-0开始执行
     * Thread-0准备休眠5秒
     * Thread-0被中断提前结束休眠,设置中断
     * Thread-0首次检查中断状态:true
     * Thread-0使用interrupted重置中断状态:true
     * Thread-0再次检查中断状态:false
     * Thread-0运行中
     * Thread-0运行中
     * Thread-0运行中
     * Thread-0被中断
     */
    public static void main(String[] args) throws InterruptedException {

        Thread thread = new RunThreadInterruptTest();
        thread.start();

        // main等待thread运行两秒
        thread.join(2000);
        thread.interrupt();

        thread.join(2000);
        thread.interrupt();
    }
}
