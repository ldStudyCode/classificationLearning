package com.learning.project.fzk.algorithm;

/**
 * author:fanzhoukai
 * 2019/7/14 16:04
 */
public class TopK {

	public static void main(String[] args) {
		int a[] = {4, 3, 5, 1, 2, 8, 9, 10};
		int result[] = new TopK().getTopKByHeap(a, 3);
		for (int temp : result) {
			System.out.println(temp);
		}
	}

	int[] createHeap(int a[], int k) {
		int[] result = new int[k];
		for (int i = 0; i < k; i++) {
			result[i] = a[i];
		}
		for (int i = 1; i < k; i++) {
			int child = i;
			int parent = (i - 1) / 2;
			int temp = a[i];
			while (parent >= 0 && child != 0 && result[parent] > temp) {
				result[child] = result[parent];
				child = parent;
				parent = (parent - 1) / 2;
			}
			result[child] = temp;
		}
		return result;

	}

	void insert(int a[], int value) {
		a[0] = value;
		int parent = 0;

		while (parent < a.length) {
			int lchild = 2 * parent + 1;
			int rchild = 2 * parent + 2;
			int minIndex = parent;
			if (lchild < a.length && a[parent] > a[lchild]) {
				minIndex = lchild;
			}
			if (rchild < a.length && a[minIndex] > a[rchild]) {
				minIndex = rchild;
			}
			if (minIndex == parent) {
				break;
			} else {
				int temp = a[parent];
				a[parent] = a[minIndex];
				a[minIndex] = temp;
				parent = minIndex;
			}
		}

	}

	int[] getTopKByHeap(int input[], int k) {
		int heap[] = this.createHeap(input, k);
		for (int i = k; i < input.length; i++) {
			if (input[i] > heap[0]) {
				this.insert(heap, input[i]);
			}


		}
		return heap;

	}
}
