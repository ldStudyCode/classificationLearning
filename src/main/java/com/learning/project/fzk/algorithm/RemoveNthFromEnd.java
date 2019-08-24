package com.learning.project.fzk.algorithm;

/**
 * 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
 * 示例：
 * 给定一个链表: 1->2->3->4->5, 和 n = 2.
 * 当删除了倒数第二个节点后，链表变为 1->2->3->5.
 * 说明：
 * 给定的 n 保证是有效的。
 * 进阶：
 * 你能尝试使用一趟扫描实现吗？
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * <p>
 * author:fanzhoukai
 * 2019/7/27 19:00
 */
public class RemoveNthFromEnd {

	public class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
		}
	}

	/**
	 * 第一次遍历：获取链表长度；
	 * 第二次遍历，找到节点，删除之。
	 * <p>
	 * 时间复杂度：O(2n)
	 * 空间复杂度：O(1)
	 */
	public ListNode removeNthFromEnd_1(ListNode head, int n) {
		// 获取链表长度
		int len = 0;
		for (ListNode h = head; h != null; h = h.next) {
			len++;
		}

		// 要删除节点的位置
		int index = len - n;

		// 单独处理删除首节点的情况（首节点无前一个元素）
		if (index == 0) {
			return head.next;
		}

		// 要删除节点的前一个元素
		ListNode prev = head;
		for (int i = 0; i < index - 1; i++) { // index-1是为了获取前一个元素
			prev = prev.next;
		}

		prev.next = prev.next.next;

		return head;
	}

	/**
	 * 一次遍历：指针i首先向前移动n步，然后指针j再出发。
	 * 这时指针i到结尾时，指针j刚好在要删除的上一个元素。
	 * <p>
	 * 时间复杂度：O(n)
	 * 空间复杂度：O(1)
	 */
	public ListNode removeNthFromEnd_2(ListNode head, int n) {

		// 虚拟头结点，避免出现临界情况
		ListNode dummy = new ListNode(0);
		dummy.next = head;

		ListNode first = dummy, second = dummy;
		for (int k = 0; k < n; k++) {
			first = first.next;
		}
		while (first.next != null) {
			first = first.next;
			second = second.next;
		}
		second.next = second.next.next;
		return dummy.next;
	}
}
