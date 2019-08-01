package space.zyzy.dubhe.concurrent.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程间通信
 * 1、synchronized 关键字等待/通知机制
 * 2、条件对象的等待/通知机制
 * Lock 替代了synchronized 方法和语句的使用，Condition 替代了 Object 监视器方法的使用。
 * Condition 实例实质上被绑定到一个锁上。要为特定 Lock 实例获得 Condition 实例，请使用其 newCondition() 方法。
 * <p>
 * 我们再次实现生产者消费者模型，代码修改子{@link RunProducerAndConsumerTest}
 */
public class RunLockConditionTest {

    // 锁对象
    private Lock lock = new ReentrantLock();

    // 生产者条件对象
    private Condition prdCondition = lock.newCondition();

    // 消费者条件对象
    private Condition conCondition = lock.newCondition();

    private static final int MAX = 20;

    private int count;

    /**
     * 注意这个方法已经没有synchronized修饰了
     */
    private void produce() {

        // 加锁
        lock.lock();
        try {

            // 达到最大库存后,生产者等待
            if (count >= MAX) {
                System.out.println(Thread.currentThread().getName() + " 库存已满");
                prdCondition.await();
            } else {
                // 模拟生产库存
                Thread.sleep(10);
                count++;
                System.out.println(Thread.currentThread().getName() + " 生产产品[" + count + "]");

                // 唤醒所有消费者
                // 此时线程释放锁
                conCondition.signalAll();
            }
        } catch (InterruptedException e) {

            // 设置线程被中断
            Thread.currentThread().interrupt();
        } finally {

            // 务必要释放锁
            lock.unlock();
        }
    }

    private synchronized void consume() {
        lock.lock();
        try {

            // 无库存,消费者等待
            if (count <= 0) {
                System.out.println(Thread.currentThread().getName() + " 无产品可消费");
                conCondition.await();
            } else {
                // 消费产品
                Thread.sleep(10);
                System.out.println(Thread.currentThread().getName() + " 消费[" + count + "]");
                count--;

                // 通知生产者可以生产了
                prdCondition.signalAll();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        final RunLockConditionTest producerAndConsumer = new RunLockConditionTest();

        // 生产者线程
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                for (; ; ) {
                    producerAndConsumer.produce();
                }
            }, "Producer-" + (i + 1)).start();
        }

        // 消费者线程
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                for (; ; ) {
                    producerAndConsumer.consume();
                }
            }, "Consumer-" + (i + 1)).start();
        }
    }
}
