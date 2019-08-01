package space.zyzy.dubhe.leetcode;

/**
 * 汉诺塔
 * 迭代法不会
 */
public class Hanoi {

    public static void main(String[] args) {
        solve(3, "A", "B", "C");
    }

    /**
     * 递归实现
     */
    public static void solve(int n, String A, String B, String C) {

        // 如果盘子只剩一个,打印移动步骤
        if (n == 1) {
            System.out.println(A + " -> " + C);
            return;
        }

        // 将n-1个盘子由A移动到B
        solve(n - 1, A, C, B);

        // 将最下面1个盘子由A移动到C
        solve(1, A, B, C);

        // 将n-1个盘子由B移动到C
        solve(n - 1, B, A, C);
    }
}
