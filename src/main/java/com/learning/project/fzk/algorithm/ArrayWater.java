package com.learning.project.fzk.algorithm;

import org.junit.Test;

import java.util.PriorityQueue;

/**
 * 给定一个数组，数组内每一个元素都形成一个柱子，各个柱子之间紧密排列。
 * 柱子之间会形成坑洼，若向坑洼中灌水，求总共能灌水的最大量。
 */
public class ArrayWater {
    /**
     * 入参：每根柱子的长度
     * 出参：每根柱子上方能存水的量
     * <p>
     * 思路：对每一根柱子，分别找到左右两边最高的柱子，取其中较小的一根，减去当前柱子的高度，就是当前柱子上能存水的量。
     * 左右两边找最高，可以给左右两边分别建一个最小堆来实现，每次移动指针，删除右堆中该元素，并放到左堆中。
     */
    @Test
    public int[] calcWater(int[] arr) {
        // 最少3根柱子才能存水
        if (arr == null || arr.length < 3) {
            throw new IllegalArgumentException("array length < 3");
        }

        // 默认结果集为0
        int[] result = new int[arr.length];

        PriorityQueue leftQueue = new PriorityQueue();
        PriorityQueue rightQueue = new PriorityQueue();

        // 建立左、右堆
        // 初始化：左堆只放第0个，右堆从第2个元素后开始放。指针从第1个元素开始遍历
        leftQueue.add(arr[0]);
        for (int i = 2; i < arr.length; i++) {
            rightQueue.add(arr[i]);
        }

        // 求每根柱子的灌水量（第一根、最后一根可跳过，因此可保证左右堆都不为空）
        for (int i = 1; i < arr.length - 1; i++) {
            int leftMax = (int) leftQueue.peek();
            int rightMax = (int) rightQueue.peek();

            // 当前柱子的存水量
            int curr = Math.min(leftMax, rightMax) - arr[i];
            if (curr > 0) {
                result[i] = curr;
            }

            // 将当前指针的元素移出右堆、插入左堆
            leftQueue.add(arr[i]);
            rightQueue.remove(arr[i]);

        }
        return result;
    }
}
