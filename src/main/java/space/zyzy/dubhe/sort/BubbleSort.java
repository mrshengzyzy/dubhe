package space.zyzy.dubhe.sort;

import java.util.Arrays;

/**
 * 冒泡排序算法的运作如下：
 * 比较相邻的元素,如果第一个比第二个大，就交换他们两个。
 * 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。这步做完后，最后的元素会是最大的数。
 * 针对所有的元素重复以上的步骤，除了最后一个。
 * 持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
 */
public class BubbleSort {

    public static void sort(int[] arr) {

        int len = arr.length;

        // 从数组右侧开始
        for (int i = len - 1; i >= 0; i--) {

            // j从0开始,跟他后面的值逐一比较
            for (int j = 0; j <= i - 1; j++) {

                // 如果当前值更大,交换
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
                System.out.println(Arrays.asList(arr));
            }

            System.out.println("第" + Math.abs(i - len) + "次排序结果" + Arrays.asList(arr));
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{2, 6, 4, 5, 3, 1};
        System.out.println("原始序列(第0次排序)：" + Arrays.asList(arr));
        sort(arr);
        System.out.println("排序后序列：" + Arrays.asList(arr));
    }
}
