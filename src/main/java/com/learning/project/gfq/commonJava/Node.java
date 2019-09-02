package com.learning.project.gfq.commonJava;

public class Node {
	// 保存每一个节点，此处为了方便直接定义成内部类
	public String data ;	// 保存节点的内容
	public Node next ;		// 保存下一个节点
	public Node(String data){
		this.data = data ;		// 通过构造方法设置节点内容
	}
	public void add(Node newNode){		// 将节点加入到合适的位置
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
	public boolean search(String data){	// 内部搜索的方法
		if(data.equals(this.data)){		// 判断输入的数据是否和当前节点的数据一致
			return true ;
		}else{	// 向下继续判断
			if(this.next!=null){	// 下一个节点如果存在，则继续查找
				return this.next.search(data) ;	// 返回下一个的查询结果
			}else{
				return false ;		// 如果所有的节点都查询完之后，没有内容相等，则返回false
			}
		}
	}
	public void delete(Node previous,String data){
		if(data.equals(this.data)){	// 找到了匹配的节点
			previous.next = this.next ;	// 空出当前的节点
		}else{
			if(this.next!=null){	// 还是存在下一个节点
				this.next.delete(this,data) ;	// 继续查找
			}
		}
	}

}
