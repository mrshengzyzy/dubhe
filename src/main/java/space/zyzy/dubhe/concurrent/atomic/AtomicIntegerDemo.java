package space.zyzy.dubhe.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo {

    public static void main(String[] args) {

//        Runnable atomicRunner = new Atomic();
//        new Thread(atomicRunner).start();
//        new Thread(atomicRunner).start();
//        new Thread(atomicRunner).start();
//        new Thread(atomicRunner).start();
//        new Thread(atomicRunner).start();

        Runnable commonRunner = new Common();
        new Thread(commonRunner).start();
        new Thread(commonRunner).start();
        new Thread(commonRunner).start();
        new Thread(commonRunner).start();
        new Thread(commonRunner).start();
    }

    /**
     * AtomicInteger更新操作具有原子性,是线程安全的
     * 假设执行次数是30,最终pointer的值就是30
     */
    static class Atomic implements Runnable {

        private final AtomicInteger pointer = new AtomicInteger(1);

        // 用于记录最终结果
        private int result;

        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                try {
                    Thread.sleep(10);
                    result = pointer.getAndIncrement();
                } catch (InterruptedException e) {
                }
            }
            System.out.println("[Atomic]:" + result);
        }
    }

    /**
     * common++不具有原子性,因此不是线程安全的
     * 假设执行次数是30,最终common的值不一定是30
     */
    static class Common implements Runnable {

        // 用于记录最终结果
        private int result = 0;

        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                try {
                    Thread.sleep(10);
                    result++;
                } catch (InterruptedException e) {
                }
            }
            System.out.println("[Common]:" + result);
        }
    }
}
