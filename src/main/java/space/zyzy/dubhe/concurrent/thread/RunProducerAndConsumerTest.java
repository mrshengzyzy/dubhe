package space.zyzy.dubhe.concurrent.thread;

/**
 * 使用 wait/notify 生产者|消费者模型
 */
public class RunProducerAndConsumerTest {

    private static final int MAX = 20;

    private int count;

    private synchronized void produce() {
        try {
            // 达到最大库存,多线程运行时会有多个线程同时达到此处
            if (count >= MAX) {
                System.out.println(Thread.currentThread().getName() + " 库存已满");

                // 释放持有的锁,线程被阻塞
                wait();
            } else {
                // 模拟生产过程
                Thread.sleep(50);
                count++;
                System.out.println(Thread.currentThread().getName() + " 生产产品[" + count + "]");

                // 注意：这里通知的是所有线程,也就是说,其他生产者线程也会被唤醒
                notifyAll();
            }
        } catch (InterruptedException e) {
            // 简化处理
        }
    }

    private synchronized void consume() {
        try {
            if (count <= 0) {
                System.out.println(Thread.currentThread().getName() + " 无产品可消费");
                wait();
            } else {
                Thread.sleep(10);
                System.out.println(Thread.currentThread().getName() + " 消费[" + count + "]");
                count--;
                notifyAll();
            }
        } catch (InterruptedException e) {
            // 简化处理
        }
    }

    public static void main(String[] args) {

        final RunProducerAndConsumerTest producerAndConsumer = new RunProducerAndConsumerTest();

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
