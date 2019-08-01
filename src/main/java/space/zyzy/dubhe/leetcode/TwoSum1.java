package space.zyzy.dubhe.leetcode;

public class TwoSum1 {

    public static int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = (i + 1); j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[2];
    }

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;

        for (int i = 0; i < nums.length; i++) {
            int next = i + 1;
            if (next < nums.length) {
                System.out.println(nums[i] + nums[next]);
            }
        }
    }
}
