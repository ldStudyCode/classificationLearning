package com.learning.project.gfq;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import com.eyang.common.servlet.barberShop.Barber;
import com.eyang.common.servlet.barberShop.Customer;

public class PvMain {

	public static void main(String[] args) {

//		pvExample
//		Producer[] p = new Producer[3];
//		Consumer[] c = new Consumer[3];
//		for(int i = 0; i < 3;i++) {
//			p[i] = new Producer(i);
//		}
//		for(int i = 0; i < 3;i++) {
//			c[i] = new Consumer(i);
//		}
//		
//		Thread[] pt = new Thread[3];
//		Thread[] ct = new Thread[3];
//		for(int i = 0; i < 3;i++) {
//			pt[i] = new Thread(p[i]);
//		}
//		for(int i = 0; i < 3;i++) {
//			ct[i] = new Thread(c[i]);
//		}
//		
//		for(int i = 0; i < 3;i++) {
//			pt[i].start();
//		}
//		for(int i = 0; i < 3;i++) {
//			ct[i].start();
//		}
		
//		try {
//			Thread.sleep(1000);
//			System.out.println(Arrays.toString(GlobalpvExample.BUFFER));
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		pipeExample
//        /**
//         * 创建管道输出流
//         */
//        PipedOutputStream pos = new PipedOutputStream();
//        /**
//         * 创建管道输入流
//         */
//        PipedInputStream pis = new PipedInputStream();
//        try {
//            /**
//             * 将管道输入流与输出流连接 此过程也可通过重载的构造函数来实现
//             */
//            pos.connect(pis);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        /**
//         * 创建生产者线程
//         */
//        PipeWrite p = new PipeWrite(pos);
//        /**
//         * 创建消费者线程
//         */
//        PipeRead c1 = new PipeRead(pis);
//        /**
//         * 启动线程
//         */
//        p.start();
//        c1.start();
		
		
//		读者写者问题
		/*
		 * 共享数据的两类使用者
		 * 	读者：只读取数据，不修改
		 * 	写者：读取和修改数据
		 * 需要满足的条件
		 * 	读读允许：同一时刻，允许有多个读者同时读
		 * 	读写互斥：没有写者时读者才能读，没有读者时写者才能写
		 * 	写写互斥：没有其他写者时写者才能写
		*/
//		Writer[] writers = new Writer[3];
//		Reader[] readers = new Reader[3];
//		for(int i = 0; i < 3;i++) {
//			writers[i] = new Writer(i);
//		}
//		for(int i = 0; i < 3;i++) {
//			readers[i] = new Reader(i);
//		}
//		
//		for(int i = 0; i < 3;i++) {
//			writers[i].start();
//		}
//		for(int i = 0; i < 3;i++) {
//			readers[i].start();
//		}
//		
//		try {
//			Thread.sleep(1000);
//			for(int i = 0; i < 3;i++) {
//				writers[i].interrupt();
//			}
//			for(int i = 0; i < 3;i++) {
//				readers[i].interrupt();
//			}
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
//		理发店问题
		/*
		 * 一个理发师，一把理发椅，n把等候理发的顾客椅子
		 * 如果没有顾客则理发师便在理发椅上睡觉 
		 * 当有一个顾客到达时，首先看理发师在干什么
		 * 如果理发师在睡觉，则唤醒理发师理发
		 * 如果理发师正在理发，则查看是否有空的顾客椅子可坐
		 * 如果有，坐下等待
		 * 如果没有，则离开
		*/
		/*
			barberShop问题：
			chairMutex = 1；//查看椅子信号量
			chairNum  = 10; //椅子数量
			barberMutex = 0； //理发师信号量
			customer = 0； //顾客信号量
			
			customer：
			//进店
			P(chairMutex);
			if(chairNum > 0) {
				V(customer);
				chairNum --;
				V(chairMutex);
				P(barberMutex);//等待理发
				//得到理发后付款
			}else {
				V(chairMutex);
				//离开;
			}
			barber:
			//看是否有顾客
			P(customer);//没有顾客就卡在这里睡觉
			P(chairMutex);
			chairNum++;
			V(chairMutex);
			//给这个人理发
			V(barberMutex);
		*/
		
		PipedInputStream CheckMoneypis = new PipedInputStream();
		PipedOutputStream CheckMoneypos = new PipedOutputStream();
		try {
			CheckMoneypos.connect(CheckMoneypis);
		} catch (IOException e) {
			e.printStackTrace(); 
		}
		
		PipedInputStream SelectHairpis = new PipedInputStream();
		PipedOutputStream SelectHairpos = new PipedOutputStream();
		try {
			SelectHairpos.connect(SelectHairpis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Customer[] customers = new Customer[100];
		for(int i = 0; i < 100; i++) {
			customers[i] = new Customer(i, i%3, CheckMoneypis, SelectHairpos);
		}
		Barber barber = new Barber(0, CheckMoneypos, SelectHairpis);
		barber.start();
		for(int i = 0; i < 100; i++) {
			customers[i].start();
		}
		
		
	}

}
