package space.zyzy.dubhe.concurrent.lock.spinlock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Ticket Lock 排队自旋锁,FIFO公平锁
 * 锁拥有一个服务号,表示正在服务的线程,还有一个排队号
 * 每个线程尝试获取锁之前先拿一个排队号,然后不断轮询锁的当前服务号是否是自己的排队号,如果是,则表示自己拥有了锁,不是则继续轮询
 * 当线程释放锁时,将服务号加1,这样下一个线程看到这个变化,就退出自旋
 * 缺点：每个线程都在读写同一个变量serviceNum,每次读写操作都必须在多个处理器缓存之间进行缓存同步
 */
public class TicketLock {

    // 服务号
    private AtomicInteger serviceNum = new AtomicInteger();

    // 排队号
    private AtomicInteger ticketNum = new AtomicInteger();

    public int lock() {

        // 首先原子性地获得一个排队号
        int myTicketNum = ticketNum.getAndIncrement();

        // 只要当前服务号不是自己的就不断轮询
        while (serviceNum.get() != myTicketNum) {
        }

        // 获取锁后的话返回当前服务号
        return myTicketNum;
    }

    public void unlock(int myTicket) {

        // 只有当前线程拥有者才能释放锁
        int next = myTicket + 1;
        serviceNum.compareAndSet(myTicket, next);
    }
}