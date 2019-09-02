package com.learning.project.gfq.pvOperation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class threadKnowledge {

	public static void main(String[] args) {

//		证明runnable中变量共享，线程不安全
//		Solution_Week3 week3 = new Solution_Week3();
//		RunnableThread  runnable = week3.new RunnableThread();
//		Thread t1 = new Thread(runnable);
//		Thread t2 = new Thread(runnable);
//		Thread t3 = new Thread(runnable);
//		Thread t4 = new Thread(runnable);
//		Thread t5 = new Thread(runnable);
//		t1.start();
//		t2.start();
//		t3.start();
//		t4.start();
//		t5.start();
		
	}
	
	
	/*
	 * 生产者消费者，结合多线程进行实现
	*/
	
	// -预备------------------------------------------------------------------
	
	//预备1
	//关于implements runnable 和 extends Thread区别
	/*
	 * 把 Thread看作线程壳，这个壳有各种资源
	 * 把runnable看作线程瓤，这个瓤里是一个runnable对象。
	 * 神奇的事情是
	 * 	当extends Thread时
	 * 		你建立了一个壳，并写了一个瓤，这时候new这个类，是new的壳+瓤，每个线程各自完全独立
	 *  当implements runnable时
	 *  	你建立了一个瓤，没有壳，这时候new这个类是new了一个runnable，在start之前还需要给他再new一个壳
	 *  	且，
	 *  	在这种runnable情况下，你起多线程，实际上是多个Thread跑同一个runnable对象，对于这个对象中的成员变量，实际上是共享使用的。
	 *  补充：
	 *  	extends Thread想实现共享成员变量，可以通过内部类的方式实现
	*/
	//1、证明implements runnable中的成员变量线程安全问题：
	//	同时证明了以上的说法
	public class RunnableThread implements Runnable{
	    private int number = 0;
	    @Override
	    public void run() {
	        for(int i = 1;i<=10000;i++) {
	        	number++;
	        }
	        System.out.println("number=" + number);
	    }
	    
	}
	
	//预备2
	//产生死锁的四个必要条件
	/*
	 * 互斥条件。每个资源要么已经分配给了一个进程，要么就是可用的
	 * 请求和等待条件。已经得到了某个资源的进程可以再请求新的资源
	 * 不剥夺\不抢占条件：任何一个资源在没被该进程释放之前，任何其他进程都无法对他剥夺占用
	 * 循环等待条件：当发生死锁时，所等待的进程必定会形成一个环路（类似于死循环），造成永久阻塞
	*/
	//破坏其中一个，就能解除死锁
	/*
	 * （难破除）解除【互斥】：独占资源变为共享资源，互斥很难破坏，且一般会特地互斥
	 * （操作系统内核常用）解除【非剥夺】：设置优先级，进行资源夺取抢占
	 * 解除【请求并等待】：每个进程运行前必须一次性申请所要求的的所有资源，如果得不到，就释放手上正在占有的资源
	 * 解除【循环等待】：资源有序分配，把资源中所有的资源编号，进程在申请资源时，必须严格按照资源编号的递增次序进行资源申请，（只对于有可能出现循环等待的，才有破坏之的意义）
	*/
	

	
	
	
}



















