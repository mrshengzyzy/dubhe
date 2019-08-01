package space.zyzy.dubhe.leetcode.topk;

import space.zyzy.dubhe.sort.HeapSort;

import java.util.Arrays;

/**
 * 堆排序解法
 * 堆排序解法跟部分排序解法类似，维护堆时间复杂度更低
 */
public class HeapTopK {

    static void solve(int[] data) {

        long start = System.currentTimeMillis();

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

        // 打印TopK结果，注意堆本身是无序的为了便于观察我们对他进行排下序
        StringBuilder sb = new StringBuilder("堆排序法结果[");
        for (int i = 9; i >= 0; i--) {
            sb.append(temp[i]);
            if (i != 0) {
                sb.append(",");
            }
        }
        sb.append("]").append("用时").append(System.currentTimeMillis() - start).append("毫秒");
        System.out.println(sb.toString());
    }
}
