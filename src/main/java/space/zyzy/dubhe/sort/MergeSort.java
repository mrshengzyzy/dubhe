package space.zyzy.dubhe.sort;

import java.util.Arrays;

/**
 * 归并排序,是创建在归并操作上的一种有效的排序算法。
 * 归并操作（merge）,也叫归并算法,指的是将两个已经排序的序列合并成一个序列的操作。
 * 归并排序算法依赖归并操作。
 * 迭代法
 * 1、申请空间,使其大小为两个已经排序序列之和,该空间用来存放合并后的序列
 * 2、设定两个指针,最初位置分别为两个已经排序序列的起始位置
 * 3、比较两个指针所指向的元素,选择相对小的元素放入到合并空间,并移动指针到下一位置
 * 4、重复步骤3直到某一指针到达序列尾
 * 5、将另一序列剩下的所有元素直接复制到合并序列尾
 * 递归法
 * 原理如下（假设序列共有n个元素）：
 * 1、将序列每相邻两个数字进行归并操作,形成 ceil(n/2)个序列,排序后每个序列包含两/一个元素
 * 2、若此时序列数不是1个则将上述序列再次归并,形成 ceil(n/4)个序列,每个序列包含四/三个元素
 * 3、重复步骤2,直到所有元素排序完毕,即序列数为1
 */
public class MergeSort {

    /**
     * 归并操作
     * 假设arr[left->(middle-1)] 和 arr[middle->right]两段都是有序的,那么归并(merge)之后,整个数组将是有序的。
     * 所以归并操作其实就是将两段有序数组重新排序的问题,如果不考虑性能,那么可以使用任意排序方法,冒泡都是可以的,
     * 但是需要考虑到两段数组都是有序的：
     * 那么采用变种选择排序更合适：
     * 因为短的一段全部放入到新数组中去了,那么长数组中剩下的那一段就可以直接拿来放入到新数组中去了
     *
     * @param arr    需要归并的数组
     * @param left   左索引
     * @param middle 分隔索引
     * @param right  右索引
     */
    static void merge(int[] arr, int left, int middle, int right) {

        // 临时数组,存放排序好数组
        int[] tempArr = new int[right - left + 1];
        int p = 0;

        // 两段索引开始位置
        int i = left, j = middle;

        // 因为两段数组长度不同,所以只要保证较短的段能被处理放到正确的位置上就OK
        while (i < middle && j <= right) {
            // 每次都要选出较小的值放置到临时数组中,并且将选出的那段的指针后移
            // arr[j++] <==> arr[j];j++; 注意这俩操作是等价的
            tempArr[p++] = arr[i] > arr[j] ? arr[j++] : arr[i++];
        }

        // 上面操作完成后,较短的数组已经被选择完毕,下面只要将较长的部分复制到临时数组
        // 两个循环其实只有一个执行
        while (i < middle) {
            tempArr[p++] = arr[i++];
        }
        while (j <= right) {
            tempArr[p++] = arr[j++];
        }

        // 复制回原数组
        for (int temp : tempArr) {
            arr[left++] = temp;
        }
    }

    /**
     * 递归实现
     * 伪代码如下：
     * mergeSort(a,left,right)
     * ----if(right>left)
     * --------middle = ceil((left+right)/2) // 计算middle位置
     * --------mergeSort(a,left,middle-1)    // 递归排序左边
     * --------mergeSort(a,middle,right)     // 递归排序右边
     * --------mergeOperation(a,left,middle,right) // 归并两边
     * <p>
     * 注意一点是,middle应该归属左段还是右段,不要弄混了
     */
    static void mergeRecursion(int[] arr, int left, int right) {
        if (left < right) {

            // 向上取整确定中点,中点属于右段
            int middle = (left + right) / 2 + 1;

            // 左段排序
            mergeRecursion(arr, left, middle - 1);

            // 右段排序
            mergeRecursion(arr, middle, right);

            // 都排序完毕后合并
            merge(arr, left, middle, right);
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{2, 6, 4, 5, 3, 1};
        mergeRecursion(arr, 0, 5);
        System.out.println(Arrays.toString(arr));
    }
}
