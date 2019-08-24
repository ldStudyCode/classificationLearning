package com.learning.project.fzk.algorithm;

/**
 * 给定 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 *
 * 说明：你不能倾斜容器，且 n 的值至少为 2。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/container-with-most-water
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MaxArea {

	public static void main(String[] args) {
		int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7};
		maxArea_2(height);
	}

	/**
	 * 双指针解法，时间O(n)
	 */
	private static int maxArea_2(int[] height) {
		int i = 0, j = height.length - 1;
		int max = 0;
		while (i < j) {
			// 计算当前面积
			int cur = (j - i) * Math.min(height[i], height[j]);
			max = cur > max ? cur : max;
			// 移动较小的那一位
			if (height[i] < height[j]) {
				i++;
			} else {
				j--;
			}
		}
		return max;
	}

	/**
	 * 暴力解法，时间O(n^2)
	 */
	public static int maxArea_1(int[] height) {
		int max = 0;
		for (int i = 0; i < height.length - 1; i++) {
			for (int j = i + 1; j < height.length; j++) {
				int cur = (j - i) * Math.min(height[i], height[j]);
				max = cur > max ? cur : max;
			}
		}
		return max;
	}
}
