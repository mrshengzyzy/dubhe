package space.zyzy.dubhe.concurrent.lockexpand;

import java.util.concurrent.Semaphore;

/**
 * 信号量Demo
 * 负责对线程进行调度,控制访问公共资源
 * 比如一个停车场,车位是固定的(信号量个数),每进一辆车减少一个车位(获取一个信号量),
 * 每出一辆车增加一个车位(释放信号量),一旦车位满了,那么后续的车只能按次序排队等待车位(公平信号量)
 * 当然了,如果来了一辆领导的车,那么他可以加塞优先停车(非公平信号量),甚至可以强行拖走里面的车释放一个车位(另一个线程释放车位)
 * 注意信号量只是控制线程是否可以执行,无法保证线程安全性
 */
public class SemaphoreDemo implements Runnable {

    // 停车场共5个车位
    private Semaphore semaphore = new Semaphore(5);

    /**
     * 停车(获取信号量)
     */
    private void park() {
        try {
            // 判断是否能获取到车位
            if (semaphore.availablePermits() == 0) {
                System.out.println(Thread.currentThread().getName() + " ======== 等待车位");
            }

            // 尝试获取车位,成功则执行,否则阻塞等待
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName() + " ++++++++ 得到车位");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 离场(释放信号量)
     */
    private void unPark() {
        try {
            Thread.sleep(5000 + (long) (Math.random() * 2000));
            System.out.println(Thread.currentThread().getName() + " -------- 释放车位");
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        park();
        unPark();
    }

    public static void main(String[] args) {
        SemaphoreDemo demo = new SemaphoreDemo();
        // 8辆车同时来停
        for (int i = 0; i < 8; i++) {
            new Thread(demo, "CAR-" + (i + 1)).start();
        }
    }
}
