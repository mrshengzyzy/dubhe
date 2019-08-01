package space.zyzy.dubhe.concurrent.thread;

import java.util.concurrent.*;

/**
 * ThreadPoolExecutor例子
 */
public class RunThreadPoolTest {
    public static void main(String[] args) throws Exception {

        // 创建一个固定大小的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        try {
            Future future = executorService.submit((Callable<Object>) () -> "callable done");
            System.out.println(future.get());
        } finally {
            executorService.shutdown();
        }
    }
}
