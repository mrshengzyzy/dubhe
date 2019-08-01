package space.zyzy.dubhe.leetcode;

/**
 * 八皇后问题
 */
public class EightQueen {

    // 记录解法
    private static int count = 0;

    // 皇后数量,棋盘大小
    private static int MAX = 8;

    // int的初始值是0,代表没有棋子
    // 放置棋子后对应的元素值改为1
    private static int[][] chessboard = new int[MAX][MAX];

    /**
     * 一行一行的摆放棋子直到最后一行
     * 如果某一行无法放置棋子的话,那么就应该回溯上一行(从递归调用点继续向下执行)
     * 而为了达到回溯的目的,必须将之前行摆放错误的棋子清除
     */
    public static void solve(int row) {

        // 最后一行摆放完毕的话,打印结果并记录第几种解法
        if (row == MAX) {
            count++;
            print();

            // 执行到这里表示发现一个解
            // 解决返回
            return;
        }

        // 每次都从第0个格子摆放
        for (int i = 0; i < MAX; i++) {

            // 检查当前位置是否可以摆放
            boolean check = check(row, i);
            if (check) {

                // 摆放棋子
                chessboard[row][i] = 1;

                /*
                 * 在当前解法的基础上继续处理下一行
                 * solve方法返回有两种：一种是解决返回,一种是回溯返回
                 * 无论哪一种,!!!!一旦solve返回,for循环将继续执行!!!!
                 * 举例: 假设i=3时调用solve(4),solve(4)回溯返回
                 * 那么for将从4开始继续循环,之前i=3摆放的棋子就是无效的,需要清除
                 */
                solve(row + 1);

                // 如果找到正确解,那么清除当前格子,继续寻找下一个正确解
                // 如果没找到正确解,那么应该从下一个格子继续尝试
                chessboard[row][i] = 0;
            }
        }

        // 执行到这里表示第row行无法摆放棋子
        // 回溯返回
    }

    /**
     * 根据横纵坐标判断棋子是否可以放置在此处,x与y的索引范围是 0 - (MAX-1)
     * check()本身可以通过分析变得更加简单,这并不是重点
     */
    private static boolean check(int x, int y) {

        // 检查范围就是最大索引范围
        for (int i = 0; i < MAX; i++) {

            // 横向检查,y的索引从 0 - (MAX-1)变化
            if (chessboard[x][i] == 1) {
                return false;
            }

            // 纵向检查,x的索引从 0 - (MAX-1)变化
            if (chessboard[i][y] == 1) {
                return false;
            }
        }

        // 左上角到右下角检查
        // x和y是同步变化的,左上角是-1右下角是+1
        for (int i = 1; i < MAX; i++) {

            // 左上角递减
            int leftX = x - i;
            int leftY = y - i;
            if (leftX >= 0 && leftY >= 0 && chessboard[leftX][leftY] == 1) {
                return false;
            }

            // 右下角递加
            int rightX = x + i;
            int rightY = y + i;
            if (rightX < MAX && rightY < MAX && chessboard[rightX][rightY] == 1) {
                return false;
            }
        }

        // 右上角到左下角检查
        // x和y不是同步变化的,右上角y增大x减小,左下角x增大y减小
        for (int i = 1; i < MAX; i++) {

            // 右上角递加
            int rightX = x - i;
            int rightY = y + i;
            if (rightX >= 0 && rightY < MAX && chessboard[rightX][rightY] == 1) {
                return false;
            }

            // 坐下角检查
            int leftX = x + i;
            int leftY = y - i;
            if (leftX < MAX && leftY >= 0 && chessboard[leftX][leftY] == 1) {
                return false;
            }
        }
        return true;
    }

    private static void print() {
        System.out.println("============第" + count + "种摆放方式============");
        for (int i = 0; i < MAX; i++) {
            for (int j = 0; j < MAX; j++) {
                System.out.print(chessboard[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        solve(0);
    }
}
