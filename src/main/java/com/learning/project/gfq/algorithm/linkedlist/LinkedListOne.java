package com.learning.project.gfq.algorithm.linkedlist;

import java.util.LinkedList;
import java.util.Stack;
import  com.learning.project.gfq.commonJava.Node;

public class LinkedListOne {
	
	public static void main(String[] args) {
		
		LinkedList<String> linkedList = new LinkedList<String>();
		linkedList.add("A");
		linkedList.add("B");
		linkedList.add("C");
		linkedList.add("D");
		System.out.println(linkedList);
		System.out.println(prac3_1(linkedList));
		
		
		Node node = new Node("1");
		node.add(new Node("2"));
		node.add(new Node("3"));
		node.add(new Node("4"));
		node.add(new Node("5"));
		prac1_reverseNode_1(node).print();
		node.print();
		prac1_reverseNode_2(node);
 		endNode.print();
	}

	

	// -将单向链表reverse，如ABCD变成DCBA，只能搜索链表一次-------------------------
	//解法1：stack站  压栈出战
	public static LinkedList<String> prac3_1(LinkedList<String> linkedList) {
		Stack<String> stack = new Stack<String>();
		while(!linkedList.isEmpty()) {
			stack.push(linkedList.getFirst());
			linkedList.removeFirst();
		}
		while(!stack.isEmpty()) {
			linkedList.add(stack.pop());
		}
		return linkedList;
	}
	
	
	//解法2：滑动窗口来解决这个问题
	public static SimpleNode prac3_2(SimpleNode node) {
		SimpleNode nextNode = null;
		SimpleNode currNextTmp = null;
		while(null != node.next) {
			//将当前节点反转
			currNextTmp = node.next;
			node.next = nextNode;
			nextNode = node;
			//转移到下一个节点
			node = currNextTmp;
		}
		//反转最后一个节点
		node.next = nextNode;
		
		return node;
	}
	
	public class SimpleNode {
		String data ;	// 保存节点的内容
		SimpleNode next ;		// 保存下一个节点
		public SimpleNode(String data){
			this.data = data ;		// 通过构造方法设置节点内容
		}
		public void add(SimpleNode newNode){		// 将节点加入到合适的位置
			if(this.next==null){			// 如果下一个节点为空，则把新节点设置在next的位置上
				this.next = newNode ;
			}else{		// 如果不为空，则需要向下继续找next
				this.next.add(newNode) ;
			}
		}
		public void print(){
			System.out.print(this.data + "\t") ;	// 输出节点内容
			if(this.next!=null){		// 还有下一个元素，需要继续输出
				this.next.print() ;	// 下一个节点继续调用print
			}
		}
	}
	
	
	// -单链表的逆序输出
	/*
	 * 法1：空间换时间
	 * 只遍历一次，新建一个node链表存储反转的链表
	*/
	public static Node prac1_reverseNode_1(Node node) {
		Node res = new Node(node.data);
		while(null != node.next) {
			Node tmp = res;
			res = new Node(node.next.data);
			res.next = tmp;
			node = node.next;
		}
		return res;
	}
	/*
	 * 法2：时间换空间
	 * 递归遍历两次，第一次遍历至末节点，第二次返回将指针反转
	*/
	public static Node endNode = null;
	public static Node prac1_reverseNode_2(Node node) {
		Node res = node;
		Node tmp = null;
		if(null != node.next) {
			tmp = prac1_reverseNode_2(node.next);
		}
		if(null != tmp) {
			res = tmp;
			res.next = node;
			node.next = null;
		}else {
			endNode = node;
		}
		return node;
	}
	
	
}
