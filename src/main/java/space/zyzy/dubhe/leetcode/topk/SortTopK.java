package space.zyzy.dubhe.leetcode.topk;

import space.zyzy.dubhe.sort.QuickSort;

/**
 * 直接排序法
 * 1、仅适用于可以把数据全部装入内存的的情况,假设我们数据是10亿,每个数据是4B,占用空间为40Billion Byte / 1000K / 1000M / 1000G = 4G
 * 2、效率低下,明明只需要TopK,把所有数据全排序无意义
 */
class SortTopK {

    static void solve(int[] data) {

        long start = System.currentTimeMillis();

        int len = data.length;

        // 快速排序
        QuickSort.sort2(data, 0, len - 1);

        // 打印结果
        StringBuilder sb = new StringBuilder("排序法结果[");
        for (int i = 0; i < 10; i++) {
            sb.append(data[len - 1 - i]);
            if (i != 9) {
                sb.append(",");
            }
        }
        sb.append("]用时").append(System.currentTimeMillis() - start).append("毫秒");
        System.out.println(sb.toString());
    }
}
