package com.learning.project.gfq.pvOperation;


/*
 * 此处应搞明白synchronized
 * 	1、锁方法时，是否锁其内部调的其他参数
*/
public class Signal {

	int signal = 0;
	
	public Signal(){
	
	}
	
	public Signal(int signal) {
		this.signal = signal;
	}
	
	public synchronized void Wait(){//关键字 synchronized 保证了此操作是一条【原语】
		signal--;
		if(signal < 0){//等于0：有一个进程进入了临界区
			try {//小于0：abs(signal)=阻塞的进程数目
				this.wait();
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void Notify() {
		signal++;
		if(signal <=0) {
			this.notify();
		}
	}
	
	
	public static void main(String[] args) {

	}

}






















