package space.zyzy.dubhe.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 快速排序使用分治法(Divide and conquer)策略来把一个序列(list)分为两个子序列(sub-lists)
 * 步骤为：
 * 从数列中挑出一个元素,称为"基准"(pivot),
 * 重新排序数列,所有比基准值小的元素摆放在基准前面,所有比基准值大的元素摆在基准后面(相同的数可以到任何一边),
 * 在这个分区结束之后,该基准就处于数列的中间位置。这个称为分区(partition)操作
 * 递归地(recursively)把小于基准值元素的子数列和大于基准值元素的子数列排序
 */
public class QuickSort {

    /**
     * 这个版本的确实现了上面的所有步骤
     * 可惜的是拿不到结果,因为们每次操作的都是新数组
     * 从每个小的数组看最终都被排序正确了,但是原数组只被操作了一次
     */
    public static void sort0(Integer[] arr) {

        // 已经没有元素或者只有一个元素的时候,排序结束
        if (arr.length == 0 || arr.length == 1) {
            return;
        }

        // 挑选第一个值作为"基准"
        int pivot = arr[0];

        // 存放比pivot小的元素
        List<Integer> leftList = new ArrayList<>();

        // 存放比pivot大的元素
        List<Integer> rightList = new ArrayList<>();

        // 第0个元素已经作为基准,所以从第1个开始比较
        for (int i = 1; i < arr.length; i++) {

            // 待比较元素
            int index = arr[i];

            // 跟pivot比较,大者放入right中,小的放入left中
            if (index > pivot) {
                rightList.add(index);
            } else {
                leftList.add(index);
            }
        }

        // 将List转换为两个数组
        Integer[] left = leftList.toArray(new Integer[leftList.size()]);
        Integer[] right = rightList.toArray(new Integer[rightList.size()]);

        // 对左边进行排序
        sort0(left);

        // 对右边进行排序
        sort0(right);
    }

    /**
     * 为了解决 sort0() 方法不能拿到返回值的问题
     * 需要每次都操作原数组,即每次都要以原数组作为参数传入,那么如何传入新排序的数组呢?
     * 很简单,将分区结果回填到原数组中,同时告知需要操作的下标范围就OK
     *
     * @param arr   需要排序的数组
     * @param start 需要排序的下标开始索引
     * @param end   需要排序的下标结束索引
     */
    public static void sort1(Integer[] arr, int start, int end) {

        // 开始索引大于等于终止索引,不需要再排序
        if (start >= end) {
            return;
        }

        // 挑选起始索引位置值作为"基准"
        int pivot = arr[start];

        // 存放比pivot小的元素
        List<Integer> leftList = new ArrayList<>();

        // 存放比pivot大的元素
        List<Integer> rightList = new ArrayList<>();

        // 第start个元素已经作为基准,所以从第start+1个开始比较,只比较到end位置
        for (int i = start + 1; i <= end; i++) {

            // 待比较元素
            int index = arr[i];

            // 跟pivot比较,大者放入right中,小的放入left中
            if (index > pivot) {
                rightList.add(index);
            } else {
                leftList.add(index);
            }
        }

        // 现在已经有了两个list和一个pivot,只需要将他们按顺序写回arr数组就好了
        // [left + pivot + right] ==> arr[start,ent]
        // 这就相当于在start->end位置被排序过了

        int leftLen = leftList.size();

        // 将leftList从start开始回填
        int x = 0;
        for (int i = start; i < start + leftLen; i++) {
            arr[i] = leftList.get(x);
            x++;
        }

        // 将pivot排在leftList后面
        arr[start + leftLen] = pivot;

        // 将rightList从pivot开始回填
        int y = 0;
        for (int i = start + leftLen + 1; i <= end; i++) {
            arr[i] = rightList.get(y);
            y++;
        }

        // 继续对左侧下标对应的数据进行排序
        sort1(arr, start, start + leftLen - 1);

        // 继续对右侧下标对应的数据进行排序
        sort1(arr, start + leftLen + 1, end);
    }

    /**
     * sort1() 已经可以进行工作,再次看下逻辑:
     * 1.取开始位置的第一个值作为pivot
     * 2.根据pivot填充leftList与rightList两个列表
     * 3.将两个列表和pivot重新填写到数组的相应位置上
     * start                    end
     * |                       |
     * leftList --- pivot --- rightList
     * 4.根据leftList长度计算后续的排序位置
     * 伪代码如下
     * quickSort(a, left, right)
     * ----if right >= left
     * ------pivot = a[left]
     * ------leftList|rightList = partition(a,left,right,pivot)
     * ------fillA(leftList,rightList)
     * ------quickSort(a, left, left+leftList.size()-1)
     * ------quickSort(a, left+leftList.size()+1, right)
     * 很显然,步骤2和步骤3很糟糕,仅仅是为了将数组特定索引间的数组排序(其实并不是真的排序,而是使用基准值分开)
     * 解决这个问题,就要使用原地(in-place)版本
     * WIKI：https://zh.wikipedia.org/wiki/%E5%BF%AB%E9%80%9F%E6%8E%92%E5%BA%8F#Java
     * 这里仅仅列出分区伪代码：
     * function partition(a, left, right, pivotIndex)
     * --pivotValue := a[pivotIndex]
     * --swap(a[pivotIndex], a[right]) // 把pivot移到結尾
     * --storeIndex := left
     * --for i from left to right-1
     * ----if a[i] <＝ pivotValue
     * --------swap(a[storeIndex], a[i])
     * --------storeIndex := storeIndex + 1
     * --swap(a[right], a[storeIndex]) // 把pivot移到它最後的地方
     * --return storeIndex
     * 看着复杂其实很简单,这个分区操作其实就是一次 选择排序 的过程
     * 想一下选择排序,每次循环先确定一个交换位置,然后从剩下的元素中选出最小的来与之交换
     * 交换位置左侧都是比当前比较值更小的值。
     * 分区操作类似，只不过不是寻找到最小值才交换，而是只要比基准值小就交换，交换位置左侧都是比基准值小的值。
     *
     * @param arr   需要排序的数组
     * @param left  需要排序的下标开始索引
     * @param right 需要排序的下标结束索引
     */
    public static void sort2(int[] arr, int left, int right) {

        // 开始索引大于等于终止索引,不需要再排序
        if (left >= right) {
            return;
        }

        // 取最右侧值为基准值
        int pivot = arr[right];

        // 交换位置索引,其左侧的值是比pivot小的值
        int pointer = left;

        // 索引从left开始向(right-1)处扫描
        for (int i = left; i < right; i++) {

            // 如果arr[i]小于等于基准值,那么应该放置pivot左侧
            if (arr[i] <= pivot) {

                // 与pointer交换
                int temp = arr[i];
                arr[i] = arr[pointer];
                arr[pointer] = temp;

                // 每次交换后,pointer自增,这样pointer左侧值都比pivot小
                pointer++;
            }
        }

        // 最后将pointer值与right交换
        // pointer的位置就是pivot最终位置,也是下一次迭代指示位置
        arr[right] = arr[pointer];
        arr[pointer] = pivot;

        // 迭代继续处理
        sort2(arr, left, pointer - 1);
        sort2(arr, pointer + 1, right);
    }

    public static void main(String[] args) {
        int[] arr = new int[]{2, 6, 4, 5, 3, 1};
        System.out.println("原始序列(第0次排序)：" + Arrays.asList(arr));
        sort2(arr, 0, arr.length - 1);
        System.out.println("排序后序列：" + Arrays.asList(arr));
    }
}
