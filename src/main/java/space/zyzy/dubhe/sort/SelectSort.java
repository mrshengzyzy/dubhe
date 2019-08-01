package space.zyzy.dubhe.sort;

import java.util.Arrays;

/**
 * 选择排序的效果是：
 * 经过几次排序后,前面的几个都已经排好序了
 * 跟冒泡排序不同的是,选择排序交换次数非常少
 * <p>
 * 1.首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置
 * 2.再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾
 * 3.重复2
 */
public class SelectSort {

    public static void sort(Integer[] arr) {

        // 从第一个元素开始向后扫描
        for (int i = 0; i < arr.length; i++) {

            // 假设当前下标是最小值下标
            int p = i;

            // 从当前位置的下一个值开始向后比较
            // 如果找到更小值记录下标值,否则扫描都不做
            for (int j = i + 1; j < arr.length; j++) {

                // 如果被比较的值更小
                if (arr[j] < arr[i]) {

                    // 记录更小值的下标
                    p = j;
                }
            }

            // 如果扫描出了更小的值,将当前值和更小值交换
            if (i != p) {
                int temp = arr[i];
                arr[i] = arr[p];
                arr[p] = temp;
            }

            System.out.println("第" + i + "次排序结果" + Arrays.asList(arr));
        }
    }

    public static void main(String[] args) {
        Integer[] arr = new Integer[]{2, 6, 4, 5, 3, 1};
        System.out.println("原始序列(第0次排序)：" + Arrays.asList(arr));
        sort(arr);
        System.out.println("排序后序列：" + Arrays.asList(arr));
    }
}
