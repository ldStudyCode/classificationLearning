package com.learning.project.ld.algorithm;

import com.google.gson.internal.$Gson$Types;
import sun.reflect.generics.tree.Tree;

import javax.sound.midi.SysexMessage;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

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
     * 前序遍历 使用stack进行遍历未判断是否重复
     * @param treeNode
     */
    public static void stackOrderTraveral(TreeNode treeNode){
        Stack<TreeNode> stack = new Stack<TreeNode>();
        //first 元素插入
        stack.push(treeNode);
        //遍历最左侧全部元素
        //前序遍历
        while(stack.peek()!=null){
            //同时满足左右子节点有值
            if(stack.peek().leftTreeNode!=null){
                System.out.println(stack.peek().data);
                stack.push(stack.peek().leftTreeNode);
            }else if(stack.peek().rightTreeNode!=null){
                System.out.println(stack.peek().data);
                stack.push(stack.peek().rightTreeNode);
            }else{
                System.out.println(stack.peek().data);
                break;
            }
        }

        while(!stack.empty()){
            TreeNode treeNodeTemp = stack.pop();
            if(stack.empty()){
                break;
            }
            TreeNode childRoot = stack.peek();
            if(childRoot.leftTreeNode==treeNodeTemp){
                if(childRoot.rightTreeNode!=null){
                    stack.push(childRoot.rightTreeNode);
                    while(stack.peek()!=null){
                        if(stack.peek().leftTreeNode!=null){
                            System.out.println(stack.peek().data);
                            stack.push(stack.peek().leftTreeNode);
                        }else if(stack.peek().rightTreeNode!=null){
                            System.out.println(stack.peek().data);
                            stack.push(stack.peek().rightTreeNode);
                        }else{
                            System.out.println(stack.peek().data);
                            break;
                        }
                    }
                }
            }
        }

    }

    /**
     * 使用stack实现中序遍历
     * @param treeNode
     */
    public static void centerOrderStackTraveral(TreeNode treeNode){
        Stack<TreeNode> stack = new Stack<TreeNode>();
        //first node
        stack.push(treeNode);
        //遍历左侧最低端节点
        while(stack.peek()!=null){
            //同时满足左子节点有值
            if(stack.peek().leftTreeNode!=null){
                stack.push(stack.peek().leftTreeNode);
            }else{
                break;
            }
        }
        while(!stack.empty()){
            TreeNode childTreeNode = stack.pop();
            System.out.println(childTreeNode.data);
            if(childTreeNode.rightTreeNode!=null){
                stack.push(childTreeNode.rightTreeNode);
                while(stack.peek().leftTreeNode!=null){
                    stack.push(stack.peek().leftTreeNode);
                }
            }
        }

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

    /**
     * 使用stack实现后序遍历
     * @param treeNode
     */
    public static void backStackTraveral(TreeNode treeNode){

    }

    public static void backOrderTraveral(TreeNode treeNode){
        if(treeNode==null)
            return;
        backOrderTraveral(treeNode.leftTreeNode);
        backOrderTraveral(treeNode.rightTreeNode);
        System.out.println(treeNode.data);
    }

    public static void main(String[] args){
        LinkedList<Integer> integerLinkedList = new LinkedList<Integer>(Arrays.asList(new Integer[]{3,2,9,null,null,10,null,7,null,null,8,null,4}));
        TreeNode treeNode = createBinaryTree(integerLinkedList);
        //System.out.println("前序遍历");
        //preOrderTraveral(treeNode);
        //stackOrderTraveral(treeNode);
        System.out.println("中序遍历");
        //centerOrderTraveral(treeNode);
        centerOrderStackTraveral(treeNode);
        System.out.println("后序遍历");
        //backOrderTraveral(treeNode);

    }
}
