package space.zyzy.dubhe.concurrent.lockexpand;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch示例
 * 调用countDownLatch.await()的线程将被阻塞
 * 子线程执行完毕后调用countDownLatch.countDown()方法
 * 当倒数计数到0时等待在await上的线程恢复执行
 */
public class CountDownLatchDemo {

    /**
     * 这里我们模拟裁判与运动员的过程,首先裁判喊准备
     * 运动员就位,然后裁判发出信号,运动员跑,所有运动员冲过终点后排名
     */
    public static void main(String[] args) throws InterruptedException {
        int playerCount = 8;
        CountDownLatch refereeLatch = new CountDownLatch(1);
        CountDownLatch playerLatch = new CountDownLatch(playerCount);

        System.out.println("裁判喊:各就位,预备~~~~~");
        Thread.sleep(500);

        // 所有运动员准备起跑
        for (int i = 0; i < playerCount; i++) {
            new Thread(new Game(refereeLatch, playerLatch), "Player-" + (i + 1)).start();
        }

        // 裁判倒计时结束,所有等待在refereeLatch.await()上的线程开始执行
        // 等待所有运动员准备完毕,没有也不影响结果,仅仅是打印结果好看
        Thread.sleep(1500);
        System.out.println("裁判喊:跑~~~~~~~");
        refereeLatch.countDown();

        // 等待所有运动员到达终点
        System.out.println("所有运动员开始跑,观众开始欢呼");
        playerLatch.await();

        // 当所有playerLatch.countDown()完成后,await执行
        System.out.println("所有运动员到达终点,裁判宣布结果");
    }

    static class Game implements Runnable {

        private CountDownLatch refereeLatch;

        private CountDownLatch playerLatch;

        Game(CountDownLatch refereeLatch, CountDownLatch playerLatch) {
            this.refereeLatch = refereeLatch;
            this.playerLatch = playerLatch;
        }

        @Override
        public void run() {
            try {

                // 运动员准备,由于refereeLatch在await,所有运动员都等待refereeLatch.countDown
                String playerName = Thread.currentThread().getName();
                System.out.println(playerName + " 准备好了");
                refereeLatch.await();

                // 运动员跑完
                int costTime = 2 + (int) (Math.random() * 10);
                Thread.sleep(costTime * 1000);
                System.out.println(playerName + " 到达终点,用时" + costTime + "秒");
                playerLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
