package space.zyzy.dubhe.concurrent.lock.spinlock;

import java.util.concurrent.atomic.AtomicReference;

/**
 *
 */
public class CLHLock {

    // 前驱节点,初始值为null（第一个线程没有前驱）
    private final ThreadLocal<Node> prev = ThreadLocal.withInitial(() -> null);

    // Node节点,每个线程将持有一个Node节点
    private final ThreadLocal<Node> node = ThreadLocal.withInitial(Node::new);

    // tail节点,指向当前运行的节点
    private final AtomicReference<Node> tail = new AtomicReference<Node>(new Node());

    public void lock() {

        // T1尝试获取锁,将自身的本地变量(ThreadLocal)设置为true
        final Node current = this.node.get();
        current.locked = true;

        //
        /**
         * T1尝试将自己链接到队列尾部,并获取到当前tail
         * 1. T1是第一个线程,那么其tail的
         */
        Node pred = this.tail.getAndSet(current);

        //
        this.prev.set(pred);

        // 当前线程在其前驱节点上自旋(检查前驱节点的locked状态)
        while (pred.locked) {
        }
    }


    public void unlock() {
        final Node node = this.node.get();
        node.locked = false;
        this.node.set(this.prev.get());
    }

    private static class Node {
        private volatile boolean locked;
    }
}