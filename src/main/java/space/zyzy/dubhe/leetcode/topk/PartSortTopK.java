package space.zyzy.dubhe.leetcode.topk;

import space.zyzy.dubhe.sort.QuickSort;

import java.util.Arrays;

/**
 * 部分排序法
 * 用一个容器保存前topK个数,只维护容器有序,遍历完成后容器中的数就是TopK
 */
class PartSortTopK {

    static void solve(int[] data) {

        long start = System.currentTimeMillis();

        // 取出前面10项并排序(升序)
        int[] temp = Arrays.copyOfRange(data, 0, 10);
        QuickSort.sort2(temp, 0, 9);

        // 遍历剩余数据跟第一个数据比较,判断是否需维护到数组中
        for (int i = 10, len = data.length; i < len; i++) {

            // 当天比较值以及数组中最小值
            int now = data[i];
            int min = temp[0];

            // 如果待比较值比最小值要大,那么就替换最小值并完成一次排序
            if (now >= min) {
                temp[0] = now;
                QuickSort.sort2(temp, 0, 9);
            }
        }

        // 打印TopK结果
        StringBuilder sb = new StringBuilder("部分排序法结果[");
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
