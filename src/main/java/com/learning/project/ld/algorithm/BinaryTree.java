package com.learning.project.ld.algorithm;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 自实现二叉树
 */
public class BinaryTree {
    /**
     * 构建二叉树
     * @param inputGroup
     * @return
     */
    public static TreeNode createBinaryTree(LinkedList<Integer> inputGroup){
        TreeNode treeNode = null;
        if(inputGroup==null||inputGroup.size()==0)
            return null;
        Integer data = inputGroup.removeFirst();
        if(null!=data){
            treeNode = new TreeNode(data);
            treeNode.leftTreeNode = createBinaryTree(inputGroup);
            treeNode.rightTreeNode = createBinaryTree(inputGroup);
        }
        return treeNode;
    }

    private static class TreeNode{
        //真实数据
        int data;
        //左子节点
        TreeNode leftTreeNode;
        //右子节点
        TreeNode rightTreeNode;
        TreeNode(int data){
            this.data = data;
        }

    }

    /**
     * 前序遍历
     * @param treeNode
     */
    public static void preOrderTraveral(TreeNode treeNode){
        if(treeNode==null)
            return;
        System.out.println(treeNode.data);
        preOrderTraveral(treeNode.leftTreeNode);
        preOrderTraveral(treeNode.rightTreeNode);

    }

    /**
     * 中序遍历
     * @param treeNode
     */
    public static void centerOrderTraveral(TreeNode treeNode){
        if(treeNode==null)
            return;
        centerOrderTraveral(treeNode.leftTreeNode);
        System.out.println(treeNode.data);
        centerOrderTraveral(treeNode.rightTreeNode);
    }

    public static void backOrderTraveral(TreeNode treeNode){
        if(treeNode==null)
            return;
        backOrderTraveral(treeNode.leftTreeNode);
        backOrderTraveral(treeNode.rightTreeNode);
        System.out.println(treeNode.data);
    }

    public static void main(String[] args){
        LinkedList<Integer> integerLinkedList = new LinkedList<Integer>(Arrays.asList(new Integer[]{3,2,9,null,null,10,null,null,8,null,4}));
        TreeNode treeNode = createBinaryTree(integerLinkedList);
        System.out.println("前序遍历");
        preOrderTraveral(treeNode);
        System.out.println("中序遍历");
        centerOrderTraveral(treeNode);
        System.out.println("后序遍历");
        backOrderTraveral(treeNode);

    }
}
