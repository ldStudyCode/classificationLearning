package com.learning.project.gfq.algorithm.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import com.learning.project.gfq.commonJava.TreeNode;
import com.learning.project.gfq.commonJava.Node;

public class TreeOne {
	
	public static void main(String[] args) {
		
		// -判断两棵树是否相等，请实现两棵树是否相等的比较，相等返回true，否则返回其他值，并说明算法复杂度
		TreeNode treeA = new TreeNode(1);
		treeA.left = new TreeNode(2);
		treeA.right = new TreeNode(3);
		treeA.left.right = new TreeNode(4);
		TreeNode treeB = new TreeNode(1);
		treeB.left = new TreeNode(3);
		treeB.right = new TreeNode(2);
		treeB.right.left = new TreeNode(4);
		System.out.println(prac8_compareTree(treeA, treeB));
		
		// -给定一个二叉搜索树(BST)，找到树中第 K 小的节点
		TreeNode root = new TreeNode(6);
		root.left = new TreeNode(5);
		root.left.left = new TreeNode(2);
		root.left.right = new TreeNode(5);
		root.right = new TreeNode(7);
		root.right.right = new TreeNode(8);
		prac3_findK(root);
		
		
		// -将二叉树的两个孩子换位置，即左变右，右变左。不能用递规
		root = prac5_switchChild(root);
		
		
		// -一棵二叉树，求最大通路长度
		System.out.println(prac12_treeLength(root));
	}

	
	// -判断两棵树是否相等，请实现两棵树是否相等的比较，相等返回true，否则返回其他值，并说明算法复杂度
	//树相等 = A树和B树的结点值相等，或子树相等，或孩子结点左右互换相等
	/*
	 * 思路，层序遍历，一层一层进行比较
	 * 时间复杂度O(n),每个结点只访问一遍
	 * 空间复杂度O(2^(n-1)),最坏情况下，最末尾一行填满元素，需开辟这么多空间进行存储
	*/
	public static boolean prac8_compareTree(TreeNode treeA, TreeNode treeB) {
		Stack<TreeNode> stackA = new Stack<TreeNode>();
		Stack<TreeNode> stackB = new Stack<TreeNode>();
		stackA.push(treeA);
		stackB.push(treeB);
		while(!stackA.isEmpty() && !stackB.isEmpty()) {
			TreeNode a = stackA.pop();
			TreeNode b = stackB.pop();
			if(a.val != b.val || a.childrenNum() != b.childrenNum()) {//比较结点操作
				return false;
			}
			//注入下一层结点操作
			if(a.childrenNum() == 1) {
				stackA.push(a.left != null?a.left:a.right);
				stackB.push(b.left != null?b.left:b.right);
			}else if(a.childrenNum() == 2) {
				if(a.left.val == b.left.val) {
					stackA.push(a.left);
					stackA.push(a.right);
					stackB.push(b.left);
					stackB.push(b.right);
				}else {
					stackA.push(a.left);
					stackA.push(a.right);
					stackB.push(b.right);
					stackB.push(b.left);
				}
			}
		}
		return true;
	}
	
	
	// -给定一个二叉搜索树(BST)，找到树中第 K 小的节点
	/*
	 * 法1：使用中序遍历，用指针存储间隔
	*/
	public static int max = 0; 
	public static int count = 0;
	public static int finalNum = 0;
	public static int k = 3;
	public static TreeNode prac3_findK(TreeNode root) {
		prac3_inOrderTravel(root);
		return null;
	}

	public static void prac3_inOrderTravel(TreeNode root) {
		if(null != root) {
			prac3_inOrderTravel(root.left);
			if(max < root.val) {
				max = root.val;
				count ++;
			}
			if(k == count) {
				count++;
				System.out.println(max);
			}
			prac3_inOrderTravel(root.right);
		}
	}

	
	// -将二叉树的两个孩子换位置，即左变右，右变左。不能用递规
	public static TreeNode prac5_switchChild(TreeNode treeNode) {
		switchChild(treeNode);
		return treeNode;
	}
	public static void switchChild(TreeNode treeNode) {
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		queue.add(treeNode);
		while(!queue.isEmpty()) {
			TreeNode currNode = queue.poll();
			if(null !=  currNode.left) {
				queue.add(currNode.left);
			}
			if(null != currNode.right) {
				queue.add(currNode.right);
			}
			TreeNode tmp = currNode.left;
			currNode.left = currNode.right;
			currNode.right = tmp;
		}
	}
	
	
	
	// -一棵二叉树，求最大通路长度。
	/*
	 * ==高度 --- 边的个数 --- 通路 ==
	 * 1、最大通路：即最大左右子树高度之和
	 * 2、树的高度：树的高度是其根结点的高度
	 * 3、结点的高度：结点的高度是该结点和某个叶子之间存在的最长路径上的边的个数
	 * 4、边：一个结点和另一个结点之间的连接被称之为边(Edge)
	 * 
	 * ==易混淆[路径、深度、层次]==
	 * 1、路径：连接结点和其后代的结点之间的(结点,边)的序列【A sequence of nodes and edges connecting a node with a descendant】
	 * 2、结点的深度：结点的深度是从树的根结点到该结点的边的个数，树的深度指的是树中结点的最大层次
	 * 3、层次:结点的层次(Level)从根(Root)开始定义起，根为第0层，根的孩子为第1层。以此类推，若某结点在第i层，那么其子树的根就在第i+1层
	 * 
	 * 总结：
	 * 	1、注意两个概念的不通方向
	 * 		高度：某结点到叶子结点之间边的个数
	 * 		深度：某结点到根结点之间边的个数
	 * 	2、注意层次从根开始，从第0层开始
	 * 	3、注意这一切长度的描述，都是描述边的个数，而非结点的个数
	 * 
	 * 参考：https://www.cnblogs.com/idorax/p/6441043.html
	*/
	public static int prac12_treeLength(TreeNode treeNode) {
		int res = 0;
		if(null != treeNode && null != treeNode.left) {
			res = res + getMaxDepth(treeNode.left);//此处未+1，getMaxDepth方法多算的1和根到左右结点的边抵消了
		}
		if(null != treeNode && null != treeNode.right) {
			res = res + getMaxDepth(treeNode.right);//此处未+1，getMaxDepth方法多算的1和根到左右结点的边抵消了
		}
		return res;
	}
	//该方法算出来的是算上根节点的最深位置结点个数，应递归结果-1为边的个数，即最大深度
	public static int getMaxDepth(TreeNode treeNode) {
		if(null == treeNode) {
			return 0;
		}
		int left = getMaxDepth(treeNode.left);
		int right = getMaxDepth(treeNode.right);
		return 1 + Math.max(left, right);
	}
	
}
