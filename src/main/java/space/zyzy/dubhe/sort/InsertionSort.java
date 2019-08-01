package space.zyzy.dubhe.sort;

import java.util.Arrays;

/**
 * 插入排序的效果：
 * 经过几次排序前面的几位就是有序的,后面的序列不变
 * <p>
 * 一般来说，插入排序都采用in-place在数组上实现。
 * 具体算法描述如下：
 * 1.从第一个元素开始，该元素可以认为已经被排序
 * 2.取出下一个元素，在已经排序的元素序列中从后向前扫描
 * 3.如果该元素（已排序）大于新元素，将该元素移到下一位置
 * 4.重复步骤3，直到找到已排序的元素小于或者等于新元素的位置
 * 5.将新元素插入到该位置后
 * 6.重复步骤2~5
 */
public class InsertionSort {

    /**
     * 自己实现的插入排序
     */
    public static void sort(int[] arr) {

        for (int i = 0; i < arr.length; i++) {

            // 第一个元素被认为是排好序的
            if (i == 0)
                continue;

            // 取出待插入排序的元素
            int p = arr[i];

            // i位置是其本身,所以从(i-1)位置向前找合适的插入位置
            for (int j = i - 1; j >= 0; j--) {

                // 待比较值
                int temp = arr[j];

                // 如果 temp 已经小于 p了,那就不需要继续比对了
                // 因为前面的已经排好序且全部小于temp
                if (temp <= p) {
                    break;
                }

                // 否则就将P和当前位置交换,多次交换后整体上看起来就是插入的效果
                arr[j + 1] = temp;
                arr[j] = p;
            }

            System.out.println("第" + i + "次排序结果" + Arrays.asList(arr));
        }
    }

    /**
     * JDK中的插入排序写法
     * 升序排列
     */
    public static void jdkSort(int[] a) {

        /*
         * for (int i = 0, j = i; i < a.length; j = ++i)
         * 换一种写法:
         * int i = 0, j = i;
         * for (; i < a.length; ){
         *     j = ++i;
         * }
         * 可以看出i和j是同步变化
         */
        for (int i = 0, j = i; i < a.length - 1; j = ++i) {

            // 同样的第1个数(索引为0)不需要排序,因此从第2个数开始,记为P
            int ai = a[i + 1];

            // 寻找插入点的过程
            // P的值在这里保持不变,而0->j位置又是排好序的
            // 因此可以使用P逐个跟前面的比较,找到插入位置然后插入
            // 插入实现的方式还是一个一个交换
            while (ai < a[j]) {

                // P的值比j点小,说明P在j前面,那么就将j点的值给P点,为后续"插入"P做准备
                // j是比较点,j+1是插入点
                a[j + 1] = a[j];

                // 直到数组开头
                if (j-- == 0) {
                    break;
                }
            }

            // 最后将p插入到插入点位置
            a[j + 1] = ai;
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{2, 3, 5, 4, 1, 6};
        System.out.println("原始序列(第0次排序)：" + Arrays.asList(arr));
        jdkSort(arr);
        System.out.println("排序后序列：" + Arrays.asList(arr));
    }
}
