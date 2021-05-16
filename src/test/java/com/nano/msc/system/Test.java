package com.nano.msc.system;

import java.util.Arrays;

/**
 * Description:
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/5/15 23:03
 */
public class Test {

    private boolean res = false;

    private int sum = 0;

    public boolean canPartition(int[] nums) {

        if (nums == null || nums.length == 0) {
            return false;
        }
        for (int i = 0; i < nums.length; i++) {
            sum = sum + nums[i];
        }
        if (sum % 2 == 1) {
            return false;
        }
        sum = sum / 2;
        Arrays.sort(nums);
        backtrack(nums, 0);
        return res;

    }

    private void backtrack(int[] nums, int index) {

        if (res) {
            return;
        }
        if (index == nums.length) {
            return;
        }

        for (int i = index; i < nums.length; i++) {
            if (nums[i] == sum) {
                res = true;
                return;
            }
            // 选择当前
            sum = sum - nums[i];
            if (sum < 0) {
                break;
            }
            backtrack(nums, i + 1);
            sum = sum + nums[i];
        }
    }

    public static void main(String[] args) {
        int[] nums = {2, 2, 1, 1};
        new Test().canPartition(nums);
    }

}
