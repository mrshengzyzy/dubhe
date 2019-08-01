package space.zyzy.dubhe.concurrent.lock.spinlock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * MCS(John Mellor-Crummey and Michael Scott)锁
 * 一种基于链表的公平的自旋锁,申请线程只在 本地变量 上自旋,直接前驱负责通知其结束自旋,从而极大地减少了不必要的处理器缓存同步的次数,降低了总线和内存的开销
 * 1、CLH锁是在其他线程(前驱)的本地变量上自旋,MCS是在自己的本地变量上自旋
 * 2、CLH的申请链是虚拟的,MCS中是真实的
 */
public class MCSLock {

    /**
     * 线程申请链,每个线程都持有他的后继
     */
    private static class MCSNode {

        // 当前线程的后继线程
        volatile MCSNode next;

        // true等待锁,false获得锁
        volatile boolean isBlock = false;
    }

    // 初始化一个空节点,对这个节点执行get()操作返回的是null
    private final AtomicReference<MCSNode> tail = new AtomicReference<>();

    private final ThreadLocal<MCSNode> node = ThreadLocal.withInitial(MCSNode::new);

    /**
     * 1. 获取当前线程的本地线程变量 node
     * 2. 通过 CAS 方法将自己设置到申请线程链的尾部,也就是 tail,同时得到其前驱节点 pre
     * 3. 如果前驱节点不是null,表示有线程持有锁,此时应该自旋等待锁,否则的话直接获取锁
     * 4. 将pre的后继设置为node,将node的block标志设置为true
     * 5. 开始在node.block上自旋等待
     */
    public void lock() {

        // 获取当前线程的QNode节点
        MCSNode now = node.get();

        // 获取前驱节点
        MCSNode predecessor = tail.getAndSet(now);

        // 前驱节点不为空,排队等待锁
        if (predecessor != null) {

            // 告诉前驱节点线程的后继为当前线程
            predecessor.next = now;

            // 等待锁标识
            now.isBlock = true;
        }

        // 开始在自己的本地变量上自旋
        // 如果前驱为null,那么 now.isBlock=false,直接获得锁
        while (now.isBlock) {
        }
    }

    /**
     * 释放锁时,要通知其后续节点结束自旋
     * 也就是修改其本地变量的值
     */
    public void unlock() {

        // 当前持有锁的节点
        MCSNode current = node.get();

        /*
         * 后继节点为null,表示current是最后一个线程,tail指向current
         * 这时候需要将tail指向null,以便后来的线程直接能够得到锁(前驱为null,lock默认false)
         */
        if (null == current.next) {

            // 将tail指向null
            if (tail.compareAndSet(current, null)) {
                return;
            }

            /*
             * 如果失败则说明tail已经不再指向null了,有其他的线程正在操作current的next节点
             * 等待其操作完成,也就是 Line48 指向的操作
             */
            while (null == current.next) {
            }
        }

        // 更改后继标志位,帮助其获得锁
        current.next.isBlock = false;

        // help GC
        current.next = null;
    }
}