package space.zyzy.dubhe.leetcode.topk;

import space.zyzy.dubhe.sort.HeapSort;
import space.zyzy.dubhe.sort.QuickSort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 将所有数据分为几个部分，每个部分均计算TopK，将所有部分的TopK结果合并，重新计算TopK
 * 假设有10亿条记录待读取，单线程必将耗费大量时间，而通过文件分割改为多线程读取可以大大降低处理时间
 * 如果对比等量数据处理，多线程优势不大，线程切换开销不可忽略
 */
public class MultiThreadTopK {

    /**
     * 计算每一部分的TopK,使用堆排序的方式计算
     */
    private static class Task implements Callable<int[]> {

        private int[] data;

        public Task(int[] data) {
            this.data = data;
        }

        @Override
        public int[] call() {

            // 取出TopK个数
            int[] temp = Arrays.copyOfRange(data, 0, 10);

            // 构造小根堆
            HeapSort.buildMaxHeapify2(temp, 10);

            // 遍历剩余数据跟第一个数据比较,判断是否需维护到数组中
            for (int i = 10, len = data.length; i < len; i++) {

                // 当前比较值以及堆中最小值(堆顶元素)
                int now = data[i];
                int min = temp[0];

                // 如果待比较值比最小值要大,那么就替换最小值并调整堆
                if (now >= min) {
                    temp[0] = now;
                    HeapSort.buildMaxHeapify2(temp, 10);
                }
            }
            return temp;
        }
    }

    private static int[] concat(int[] a, int[] b) {
        int[] c = new int[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    static void solve(int[] data) {

        long start = System.currentTimeMillis();

        // 分成4片处理
        int fragment = 4;

        // 理论上每一片大小
        int len = data.length;
        int fragmentSize = len / fragment;

        // 使用4个线程来处理任务
        ExecutorService executorService = Executors.newFixedThreadPool(fragment);

        // 添加任务
        List<Callable<int[]>> taskList = new ArrayList<>();
        for (int i = 0; i < fragment; i++) {
            if (i != fragment - 1) {
                taskList.add(new Task(Arrays.copyOfRange(data, fragmentSize * i, fragmentSize * (i + 1))));
            } else {
                taskList.add(new Task(Arrays.copyOfRange(data, fragmentSize * i, len)));
            }
        }

        try {

            int[] arr = new int[0];

            // 提交任务
            List<Future<int[]>> futureList = executorService.invokeAll(taskList);
            for (Future<int[]> future : futureList) {

                // 合并计算结果
                arr = concat(arr, future.get());
            }

            // 再次计算汇总之后arr的topK,这个数据少直接使用快排
            QuickSort.sort2(arr, 0, arr.length - 1);

            // 打印结果
            StringBuilder sb = new StringBuilder("多线程TopK结果[");
            for (int i = 0; i < 10; i++) {
                sb.append(arr[arr.length - 1 - i]);
                if (i != 9) {
                    sb.append(",");
                }
            }
            sb.append("]用时").append(System.currentTimeMillis() - start).append("毫秒");
            System.out.println(sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}
