package space.zyzy.dubhe.sort;

import java.util.Arrays;

/**
 * 堆排序
 * 堆是一种特殊的二叉树,具备以下两种性质
 * 1）每个节点的值都大于（或小于,称为最小堆,小根堆）其子节点的值
 * 2）是完全二叉树 Complete Binary Tree
 * 假设有如下完整二叉树：
 * ________4
 * ______/__\
 * _____2____1
 * ____/ \
 * ___3___5
 * 那么使用数组可以存储该二叉树[4,2,1,3,5]
 * 他们的位置关系为：
 * 假设父节点索引为P(从0开始),那么左节点2P+1,右节点为2P+2
 * 假设一个节点是M,那么父节点时 floor((M-1)/2)
 * ----------
 * |-----   |
 * |    |   |
 * 4    2   1   3   5
 * _____|       |   |   树的边之间的关系
 * _____-------------
 * 0    1   2   3   4   数组索引
 * 堆排序过程
 * 1.最大堆调整（Max_Heapify）：将堆的末端子节点作调整,使得子节点永远小于父节点
 * 2.创建最大堆（Build_Max_Heap）：递归的调整每一个节点直至堆顶元素
 * // 通过上面两步我们可以找到堆顶元素
 * 3.堆排序（HeapSort）：移除位在第一个数据的根节点,并做最大堆调整的递归运算(调整完毕后,arr[0]即是堆顶元素)
 * // 移除堆顶元素继续构建堆
 * 参考：
 * https://www.cnblogs.com/MOBIN/p/5374217.html
 */
public class HeapSort {

    /**
     * 第一版本的堆排序
     * 假设数组转换成完全二叉树后,最后一个节点索引为n
     * 那么它的父节点记做 K = n-1/2
     * 显然我们只需要调整 0-K 的节点就好了
     * K后面的节点都没有子节点,他们将会在调整其对应的父节点时同时被调整
     * 因为调整之后的树都变成有序堆,显然调整方向为 K->0
     */
    public static void sort0(int[] arr, int lastIndex) {

        // 只剩一个元素不必调整
        if (lastIndex == 0) {
            return;
        }

        // 最后一个父节点索引
        int lastParentNodeIndex = (lastIndex - 1) / 2;

        // 从后向前调整
        for (int i = lastParentNodeIndex; i >= 0; i--) {

            // 调整索引从0-i开始使成为大根堆
            maxHeapify0(arr, i, lastIndex);
        }

        // 调整完毕后交换堆顶和堆尾两个元素
        swap(arr, 0, lastIndex);

        // 再次对除了最后一个元素的数组的数据
        sort0(arr, lastIndex - 1);
    }

    /**
     * 调整为大根堆
     * 1.从index向后调整
     * 1.找到所有子节点
     * 2.跟子节点比较,并进行交换
     * 3.如果子节点还有子节点,继续调整
     * =========================================
     * 需要注意不能从任意节点开始而是只能从最后一个有效节点开始
     *
     * @param arr       原始数组
     * @param index     开始调整位置
     * @param lastIndex 需要调整数组的最后一个索引
     */
    private static void maxHeapify0(int[] arr, int index, int lastIndex) {

        // 左节点对应的索引
        int leftIndex = 2 * index + 1;

        // 左节点对应的值(仅做一个标识用)
        int leftValue = -1;

        // 当且仅当是有效索引时才允许获取值
        if (leftIndex <= lastIndex) {
            leftValue = arr[leftIndex];
        }

        // 右节点对应的索引
        int rightIndex = 2 * index + 2;

        // 右节点对应的值
        int rightValue = -1;

        // 当且仅当是有效索引时才允许获取值
        if (rightIndex <= lastIndex) {
            rightValue = arr[rightIndex];
        }

        // 比较左右节点,找到较大值的索引
        int maxIndex;

        // 没有子节点说明都不做
        if (rightValue == -1 && leftValue == -1) {
            maxIndex = index;
        } else if (rightValue != -1 && leftValue != -1) {
            // 有两个子节点就返回较大值的索引
            maxIndex = leftValue > rightValue ? leftIndex : rightIndex;
        } else if (rightValue == -1) {
            // 右节点为空就返回左节点索引
            maxIndex = leftIndex;
        } else {
            // 左节点为空就返回右节点索引
            maxIndex = rightIndex;
        }

        // 再与根节点比较,判断是否交换
        if (arr[maxIndex] > arr[index]) {
            swap(arr, maxIndex, index);
        }

        // 如果子节点还有子节点,递归调整
        if (2 * leftIndex + 1 <= lastIndex) {
            maxHeapify0(arr, leftIndex, lastIndex);
        }
        if (2 * rightIndex + 1 <= lastIndex) {
            maxHeapify0(arr, rightIndex, lastIndex);
        }
    }

    // ==============================================================================

    /**
     * 堆调整（大根堆，堆顶元素是最大值）
     * 堆是一棵完全二叉树，存储结构为数组，判断一个节点是否存在跟数组长度判断就可以
     * 比如数组：[2,3,4,5]
     * 那么堆结构如下：
     * _____2
     * __3____4
     * _5
     * 假设判断3的子节点存在情况
     * 3对应的数组索引是1，那么他的左子节点索引 2*1+1 = 3，右子节点索引 = 左子节点索引+1 = 4
     * 整个数组索引范围是 A[0-3],判断关系是 NodeIndex <= A  <==> NodeIndex < (A+1) = Arrays.Length
     * 因此可以判断对于3来说左子节点存在，右子节点不存在
     * =====================================
     * 需要注意不能从任意节点开始而是只能从最后一个有效节点开始
     *
     * @param arr    待排序数组
     * @param index  起始排序索引
     * @param length 排序数组长度,这个长度并不是arr.length,因为我们可能只排序数组中的一部分
     */
    public static void maxHeapify1(int[] arr, int index, int length) {

        int left, right, pointer;

        // 根据index节点计算左节点索引,左节点存在的话才需要调整
        while ((left = 2 * index + 1) < length) {

            // 右节点索引
            right = left + 1;

            // 当前指针记录交换位置
            pointer = index;

            // 比较当前节点值和左子节点值,记录较大值的索引
            if (arr[index] < arr[left]) {
                pointer = left;
            }

            // 比较pointer值与右子节点值,记录较大值索引
            if (right < length && arr[pointer] < arr[right]) {
                pointer = right;
            }

            // pointer不等于初始值index,那就说明需要交换
            if (pointer != index) {
                swap(arr, index, pointer);

                // 交换之后被交换的节点可能不在满足堆性质,继续交换
                index = pointer;
            } else {
                break;
            }
        }
    }

    /**
     * 完成一趟堆调整
     * 对于具有N个节点的堆来说,只需从N/2个开始调整
     */
    public static void buildMaxHeapify1(int[] arr, int length) {
        // 最后一个具有子节点的节点,也是由后向前开始调整的第一个节点
        int start = length / 2 - 1;
        for (int i = start; i >= 0; i--) {
            maxHeapify1(arr, i, length);
        }
    }

    /**
     * 第二版堆排序
     * 堆排序每次只能筛选一个数据,因此需要进行n趟排序
     */
    public static void sort1(int[] arr) {
        int len = arr.length;

//        // 第一趟排序
//        buildMaxHeapify(arr, len);
//        // 第一趟排序完成后交换第一个与最后一个值
//        swap(arr, 0, len - 1);
//
//        // 第二趟再排[0-(n-1)]个数据
//        buildMaxHeapify(arr, len - 1);
//        swap(arr, 0, len - 2);

        // 写成循环形式就是
        for (int i = 0; i < len; i++) {
            buildMaxHeapify1(arr, len - i);
            swap(arr, 0, len - i - 1);
        }
    }

    // ==============================================================================

    /**
     * 小根堆（堆顶元素是最小值）
     */
    public static void maxHeapify2(int[] arr, int index, int length) {

        int left, right, pointer;

        // 根据index节点计算左节点索引,左节点存在的话才需要调整
        while ((left = 2 * index + 1) < length) {

            // 右节点索引
            right = left + 1;

            // 当前指针记录交换位置
            pointer = index;

            // 比较当前节点值和左子节点值,记录较小值的索引
            if (arr[index] > arr[left]) {
                pointer = left;
            }

            // 比较pointer值与右子节点值,记录较大值索引
            if (right < length && arr[pointer] > arr[right]) {
                pointer = right;
            }

            // pointer不等于初始值index,那就说明需要交换
            if (pointer != index) {
                swap(arr, index, pointer);

                // 交换之后被交换的节点可能不在满足堆性质,继续交换
                index = pointer;
            } else {
                break;
            }
        }
    }
    public static void buildMaxHeapify2(int[] arr, int length) {
        // 最后一个具有子节点的节点,也是由后向前开始调整的第一个节点
        int start = length / 2 - 1;
        for (int i = start; i >= 0; i--) {
            maxHeapify2(arr, i, length);
        }
    }

    // ==============================================================================

    private static void swap(int[] arr, int x, int y) {
        int temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {7, 9, 1, 3, 2, 5, 6, 4, 8};
        System.out.println("原始数据序列" + Arrays.toString(arr));
        sort1(arr);
        System.out.println("排序后序列" + Arrays.toString(arr));
    }
}
