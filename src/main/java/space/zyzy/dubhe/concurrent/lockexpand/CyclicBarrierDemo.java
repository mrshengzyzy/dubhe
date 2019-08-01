package space.zyzy.dubhe.concurrent.lockexpand;

import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrierDemo示例
 * 调用CyclicBarrierDemo.await()的线程将被相互阻塞
 * 直到所有的线程都执行到这个阻塞点,然后继续执行
 * 如果在创建屏障的时候指定了Runnable任务,那么在唤醒阻塞线程时将首先执行该任务
 */
public class CyclicBarrierDemo {

    /**
     * 1、所有人到指定地点集合签到
     * 2、所有人到了之后点名
     * 3、然后一个一个开始上车
     */
    public static void main(String[] args) {

        /*
         * 创建5个屏障
         * 并定义一个BarrierAction方法
         */
        int peopleNum = 5;
        CyclicBarrier barrier = new CyclicBarrier(peopleNum, () -> {
            // 执行这里的线程将是最后一个到达屏障的线程
            System.out.println(Thread.currentThread().getName() + "说: 人到齐了,开始上车");
        });

        for (int i = 0; i < peopleNum; i++) {
            new Thread(new Trip(barrier)).start();
        }
    }

    static class Trip implements Runnable {

        private CyclicBarrier cyclicBarrier;

        Trip(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {

                // 所有人员到了之后先签到
                int costTime = 2 + (int) (Math.random() * 10);
                Thread.sleep(costTime * 1000);
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName + " 已到达,签到完毕,等待其他人...");

                // 等待其他人到来,当前线程被阻塞,BarrierAction将被执行
                cyclicBarrier.await();

                // BarrierAction执行完毕
                System.out.println(threadName + " 开始上车");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
