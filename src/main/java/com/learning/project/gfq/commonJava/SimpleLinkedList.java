package com.learning.project.gfq.commonJava;

public class SimpleLinkedList {
	
	
	
	private Node root ;		// 链表中必然存在一个根节点
	public void addNode(String data){	// 增加节点
		Node newNode = new Node(data) ;	// 定义新的节点
		if(this.root==null){			// 没有根节点
			this.root = newNode ;	// 将第一个节点设置成根节点
		}else{		// 不是根节点，放到最后一个节点之后
			this.root.add(newNode) ;	// 通过Node自动安排此节点放的位置
		}
	}
	public void printNode(){		// 输出全部的链表内容
		if(this.root!=null){		// 如果根元素不为空
			this.root.print() ;	// 调用Node类中的输出操作
		}
	}
	public boolean contains(String name){	// 判断元素是否存在
		return this.root.search(name) ;	// 调用Node类中的查找方法
	}
	public void deleteNode(String data){		// 删除节点
		if(this.contains(data)){	// 判断节点是否存在
			// 一定要判断此元素现在是不是根元素相等的
			if(this.root.data.equals(data)){	// 内容是根节点
				this.root = this.root.next ;	// 修改根节点，将第一个节点设置成根节点
			}else{
				this.root.next.delete(root,data) ;	// 把下一个节点的前节点和数据一起传入进去
			}
		}
	}
}
