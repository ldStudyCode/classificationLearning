package com.learning.project.fzk.algorithm;

/**
 * 反转单链表
 * <p>
 * author:fanzhoukai
 * 2019/7/20 21:44
 */
public class ReverseList {

	public static void main(String[] args) {
		ListNode n1 = new ListNode(1);
		ListNode n2 = new ListNode(2);
		ListNode n3 = new ListNode(3);
		n1.next = n2;
		n2.next = n3;

		ListNode reverse = reverseList(n1);

		reverse.print();

	}

	public static class ListNode {
		private Object value;
		private ListNode next;

		public ListNode(Object value) {
			this.value = value;
		}

		public void print() {
			for (ListNode n = this; n != null; n = n.next) {
				System.out.print(n.value + " ");
			}

		}
	}

	public static ListNode reverseList(ListNode head) {
		ListNode result = null; //反转过后的单链表存储头结点
		ListNode curr = head;
		ListNode prev = null; //存储前一个结点
		while (curr != null) {
			ListNode next = curr.next;
			//如果curr的下一个结点为空，则curr即为结果
			if (next == null) {
				result = curr;
			}
			curr.next = prev;
			prev = curr;
			curr = next;
		}
		return result;
	}

}
