package com.learning.project.gfq.commonJava;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class TreeNode {

	public int val;
	public TreeNode left;
	public TreeNode right;
	public TreeNode parent;

	public TreeNode(int val) {
		this.val = val;
	}
	
	public int childrenNum() {
		int res = 0;
		if(null != this.left) {
			res++;
		}
		if(null != this.right) {
			res++;
		}
		return res;
	}
	
	
	//补充：树的前序遍历、中序遍历、后序遍历实现
	//前序遍历
	public static void preOrderTravel(TreeNode root) {
		if(null != root) {
			System.out.println(root.val);
			preOrderTravel(root.left);
			preOrderTravel(root.right);
		}
	}
	/*
	 * 前序遍历形成了尾递归，因此优化代码
	*/
	
	public static void preOrderTravel_better(TreeNode root) {
		Stack<TreeNode> stack = new Stack<TreeNode>();
		while(null != root || !stack.isEmpty()) {
			while(null != root) {
				System.out.println(root.val);
				stack.push(root);
				root = root.left;
			}
			if(!stack.isEmpty()) {
				root = stack.pop();
				root = root.right;
			}
		}
	}
	
	//中序遍历
	public static void inOrderTravel(TreeNode root) {
		if(null != root) {
			inOrderTravel(root.left);
			System.out.println(root.val);
			inOrderTravel(root.right);
		}
	}
	
	//中序遍历优化
	public static void inOrderTravel_better(TreeNode root) {
		Stack<TreeNode> stack = new Stack<TreeNode>();
		while(null != root || !stack.isEmpty()) {
			while(null != root) {
				stack.push(root);
				root = root.left;
			}
			if(!stack.isEmpty()) {
				root = stack.pop();
				System.out.println(root.val);
				root = root.right;
			}
		}
	}
	
	//后序遍历
	public static void postOrderTravel(TreeNode root) {
		if(null != root) {
			postOrderTravel(root.left);
			postOrderTravel(root.right);
			System.out.println(root.val);
		}
	}
	
	
	//算不上优化的堆实现
	/*
	 * 法1：利用栈的先进后出特性
	 * 后序为左右根，因此可以先按根右左的方式压栈，然后再遍历出来即可，而跟右左可以通过改编前序遍历实现
	*/
	public static void postOrderTravel_better_1(TreeNode root) {
		Stack<TreeNode> stackPre = new Stack<TreeNode>();
		Stack<TreeNode> stackPost = new Stack<TreeNode>();//这个栈由于存了全部结点，占用空间较大
		while(null != root || !stackPre.isEmpty()) {
			while(null != root) {
				stackPost.push(root);//应当进行操作的位置
				stackPre.push(root);
				root = root.right;
			}
			if(!stackPre.isEmpty()) {
				root = stackPre.pop();
				root = root.left;
			}
		}
		while(!stackPost.isEmpty()) {
			System.out.println(stackPost.pop().val);
		}
		stackPost.peek();//取出栈顶元素，且不删除他（pop会删除他）
	}
	/*
	 * 法2:利用一个指针保存上一次操作的结点，通过判断当前结点与上一个结点的位置关系，决定堆当前结点如何操作
	*/
	public static void postOrderTravel_better_2(TreeNode root) {
		Stack<TreeNode> stack = new Stack<TreeNode>();
		stack.push(root);
		TreeNode curr = null;
		TreeNode pre = null;
		while(!stack.isEmpty()) {
			curr = stack.peek();
			if(null == pre || pre.left == curr || pre.right == curr) {//若上一个结点是他的父亲结点，则将其左/右结点入栈
				if(null != curr.left) {
					stack.push(curr.left);
				}else if(null != curr.right) {
					stack.push(curr.right);
				}
			}else if(curr.left == pre) {
				if(null != curr.right) {
					stack.push(curr.right);
				}
			}else {
				System.out.println(curr.val);
				stack.pop();
			}
			pre = curr;
		}
	}
	
	//层序遍历
	/*
	 * 利用队列的先进先出，进行逐层遍历
	*/
	public static void levelOrderTravel(TreeNode root) {
		if(null == root) {
			return;
		}
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		queue.add(root);
		while(!queue.isEmpty()) {
			TreeNode node = queue.poll();
			if (null != node.left) {
				queue.add(node.left);
			}
			if(null != node.right) {
				queue.add(node.right);
			}
			System.out.println(node.val);
		}
	}

}
