package com.learning.project.fzk.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一组不含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
 * 说明：解集不能包含重复的子集。
 * 示例:
 * 输入: nums = [1,2,3]
 * 输出:
 * [
 * [3],
 *   [1],
 *   [2],
 *   [1,2,3],
 *   [1,3],
 *   [2,3],
 *   [1,2],
 *   []
 * ]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/subsets
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * author:fanzhoukai
 * 2019/7/27 23:36
 */
public class Subsets {

	public static void main(String[] args) {
		int[] nums = {0,1,2,3,4};
		List<List<Integer>> subsets = subsets(nums);
		for (List<Integer> subset : subsets) {
			for (Integer i : subset) {
				System.out.print(i+" ");
			}
			System.out.println();
		}
	}

	public static List<List<Integer>> subsets(int[] nums) {
		List<List<Integer>> list = new ArrayList<>();

		int size = nums.length;
		for (int i = 0; i < (1 << size); i++) {
			List<Integer> ax = new ArrayList<>();
			for (int j = 0; j < size; j++) {
				if ((i & (1 << j)) != 0) {
					ax.add(nums[size - j - 1]);
				}
			}
			list.add(ax);
		}
		return list;
	}
}
