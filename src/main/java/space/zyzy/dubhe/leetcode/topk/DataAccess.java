package space.zyzy.dubhe.leetcode.topk;

import java.util.Random;

public final class DataAccess {

    /**
     * 返回长度为len的int数组
     * 每个值的范围是0-len
     */
    public static int[] get(int len) {
        int[] storage = new int[len];
        Random r = new Random();
        for (int i = 0; i < len; i++) {
            storage[i] = r.nextInt(len);
        }
        return storage;
    }
}
