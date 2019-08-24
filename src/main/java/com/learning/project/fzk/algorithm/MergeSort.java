package com.learning.project.fzk.algorithm;

/**
 * 归并排序
 * <p>
 * author:fanzhoukai
 * 2019/7/14 15:11
 */
public class MergeSort {
	public static void main(String[] args) {
		int[] arr = {11, 44, 23, 67, 88, 65, 34, 48, 9, 12};
		mergeSort(arr);
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
	}

	private static void mergeSort(int[] arr) {
		int[] tmp = new int[arr.length];    //新建一个临时数组存放
		mergeSort(arr, 0, arr.length - 1, tmp);
	}

	public static void mergeSort(int[] arr, int low, int high, int[] tmp) {
		if (low < high) {
			int mid = (low + high) / 2;
			mergeSort(arr, low, mid, tmp); //对左边序列进行归并排序
			mergeSort(arr, mid + 1, high, tmp);  //对右边序列进行归并排序
			merge(arr, low, mid, high, tmp);    //合并两个有序序列
		}
	}

	public static void merge(int[] arr, int low, int mid, int high, int[] tmp) {
		int i = 0;
		int j = low, k = mid + 1;  //左边序列和右边序列起始索引
		while (j <= mid && k <= high) {
			if (arr[j] < arr[k]) {
				tmp[i++] = arr[j++];
			} else {
				tmp[i++] = arr[k++];
			}
		}
		//若左边序列还有剩余，则将其全部拷贝进tmp[]中
		while (j <= mid) {
			tmp[i++] = arr[j++];
		}

		while (k <= high) {
			tmp[i++] = arr[k++];
		}

		for (int t = 0; t < i; t++) {
			arr[low + t] = tmp[t];
		}
	}
}
