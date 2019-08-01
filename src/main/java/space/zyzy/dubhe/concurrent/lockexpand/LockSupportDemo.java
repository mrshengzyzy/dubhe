package space.zyzy.dubhe.concurrent.lockexpand;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport类测试
 * 参照 http://blog.csdn.net/hengyunabc/article/details/28126139
 */
public class LockSupportDemo implements Runnable {

    private int loop = 0;

    @Override
    public void run() {

        for (; ; ) {
            try {

                TimeUnit.MILLISECONDS.sleep(500);

                int loopPlus = loop++;

                if (loopPlus == 5) {
                    System.out.println("循环次数达到5 --- park");
                    LockSupport.park();
                    System.out.println("park线程识别到有park许可继续执行");
                }

                if (loopPlus == 10) {
                    System.out.println("循环次数达到10 --- 再次park");
                    LockSupport.park();
                }

                System.out.println("当前循环次数" + loopPlus);
            } catch (InterruptedException e) {
                System.out.println("park中的线程能够响应中断");
                return;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        LockSupportDemo demo = new LockSupportDemo();
        Thread thread = new Thread(demo);

        // 启动线程
        thread.start();

        // 5秒后给thread一个许可,线程将从park的地方继续执行
        TimeUnit.SECONDS.sleep(5);
        LockSupport.unpark(thread);

        // 5秒后中断线程,park中的线程可以响应中断
        TimeUnit.SECONDS.sleep(5);
        thread.interrupt();
    }
}
