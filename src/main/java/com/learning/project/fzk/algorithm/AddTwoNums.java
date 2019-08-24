package com.learning.project.fzk.algorithm;

/**
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * 示例：
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/add-two-numbers
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * <p>
 * author:fanzhoukai
 * 2019/7/3 23:40
 */
public class AddTwoNums {
	/**
	 * 时间复杂度：O(max(m,n))
	 * 空间复杂度：O(max(m,n))
	 */
	public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode result = new ListNode(0); // 头节点，避免每次判断是否存在。最后取结果时去除头结点
		ListNode lastNode = result;
		boolean plusOne = false; // 是否进位
		while (l1 != null || l2 != null || plusOne) {
			int current = (l1 == null ? 0 : l1.val) + (l2 == null ? 0 : l2.val) + (plusOne ? 1 : 0);
			if (current < 10) { // 不进位
				lastNode.next = new ListNode(current);
				plusOne = false;
			} else {
				lastNode.next = new ListNode(current - 10);
				plusOne = true;
			}
			lastNode = lastNode.next;

			// 进位
			l1 = l1 != null ? l1.next : null;
			l2 = l2 != null ? l2.next : null;
		}
		return result.next;
	}

	public static class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
		}

		public void print() {
			for (ListNode i = this; i != null; i = i.next) {
				System.out.print(i.val);
			}
		}
	}

	public static void main(String[] args) {
		ListNode n1_1 = new ListNode(2);
		n1_1.toString();
		ListNode n1_2 = new ListNode(4);
		ListNode n1_3 = new ListNode(3);
		n1_1.next = n1_2;
		n1_2.next = n1_3;

		ListNode n2_1 = new ListNode(5);
		ListNode n2_2 = new ListNode(6);
		ListNode n2_3 = new ListNode(4);
		n2_1.next = n2_2;
		n2_2.next = n2_3;

		addTwoNumbers(n1_1, n2_1).print();
	}
}
