package space.zyzy.dubhe.concurrent.lock.spinlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁
 */
public class EasySpinLock {

    private final AtomicReference<Thread> owner = new AtomicReference<>();

    public void lock() {
        while (!owner.compareAndSet(null, Thread.currentThread())) {
            System.out.println(Thread.currentThread().getName() + " waiting");
        }
        System.out.println(Thread.currentThread().getName() + " got lock");
    }

    public void unlock() {
        owner.compareAndSet(Thread.currentThread(), null);
    }

    // ========================================================================

    static class TestClass implements Runnable {

        private int pointer;

        EasySpinLock spinLock = new EasySpinLock();

        @Override
        public void run() {
            for (int j = 0; j < 10; j++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                    // 锁粗化
                    spinLock.lock();
                    pointer++;
                } catch (InterruptedException e) {
                    //
                } finally {
                    spinLock.unlock();
                }
            }
            System.out.println(pointer);
        }
    }

    public static void main(String[] args) {
        TestClass testClass = new TestClass();
        for (int i = 0; i < 5; i++) {
            new Thread(testClass, "Thread-" + i).start();
        }
    }
}