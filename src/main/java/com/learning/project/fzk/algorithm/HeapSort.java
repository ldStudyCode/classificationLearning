package com.learning.project.fzk.algorithm;

import org.testng.annotations.Test;

/**
 * 堆排序
 *
 * author:fanzhoukai
 * 2019/7/27 23:20
 */
public class HeapSort {

	@Test
	public void test() {
		int[] array = {3, 0, 8, 9, 1, 5, 4, 2, 7, 1, 2};
		HeapSort.sort(array);
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
	}

	/**
	 * 堆排序方法
	 */
	public static void sort(int[] a) {
		// 堆的构造阶段
		int N = a.length;  // 取得节点总数
		for (int i = N / 2; i > 0; i--) {
			sink(a, i, N); // 对所有父节点，从最后一个父节点到根节点，依次下沉排序
		}  // 到这里数组已经完全堆有序
		// 下沉排序阶段
		while (N > 1) {
			exchange(a, 1, N); // 将数组中最大的元素放到数组后端
			N--; // 将最大的节点元素移出堆
			sink(a, 1, N); // 下沉操作，再次实现堆有序
		}
	}

	/**
	 * 交换两个数组元素的值
	 * 注意！ 不同于一般的exchange, 这里的i和j要减1！
	 */
	private static void exchange(int[] a, int i, int j) {
		int temp = a[i - 1];
		a[i - 1] = a[j - 1];
		a[j - 1] = temp;
	}

	/**
	 * 比较i和j下标的数组元素的大小
	 * 注意！ 不同于一般的less, 这里的i和j要减1！
	 */
	private static boolean less(int[] a, int i, int j) {
		return a[i - 1] - a[j - 1] < 0 ? true : false;
	}

	/**
	 * 下沉操作
	 *
	 * @param a 待排序数组
	 * @param k 堆中的节点位置
	 * @param N 堆中的节点总数
	 */
	private static void sink(int[] a, int k, int N) {
		while (2 * k <= N) { // 当该节点存在至少一个子节点的时候
			int j = 2 * k;  // 取得左儿子的位置
			if (j < N && less(a, j, j + 1)) {
				j++;
			} // 取得左儿子和右儿子中的较大者
			if (less(a, k, j)) {  // 当该节点的值小于较大的左儿子的时候
				exchange(a, k, j); // 交换它和该儿子节点的值
				k = j; // 取得该儿子节点的位置
			} else {
				break;
			}
		}
	}
}
