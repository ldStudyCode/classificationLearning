package com.learning.project.gfq.pvOperation.barberShop;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Customer extends Thread {
	
	int id = 0;
	int hairNum = 0;//所选发型编号
	PipedInputStream CheckMoneypis;//接收付款二维码管道
	PipedOutputStream SelectHairpos;//选择发型管道
	
	public final Map<Integer, String> hairMenu = new HashMap<Integer, String>();
	
	public Customer(int id, int hairNum, PipedInputStream CheckMoneypis, PipedOutputStream SelectHairpos) {
		this.id = id;
		this.hairNum = hairNum;
		this.CheckMoneypis = CheckMoneypis;
		this.SelectHairpos = SelectHairpos;
	}
	
	@Override
	public void run() {
		hairMenu.put(0, "卷发,10元");
		hairMenu.put(1, "光头,15元");
		hairMenu.put(2, "黑长直,100元");
		GlobalBarberShop.chairMutex.Wait();
		if(GlobalBarberShop.chairNum > 0) {
			GlobalBarberShop.chairNum --;
			GlobalBarberShop.customerMutex.Notify();
			try {
				SelectHairpos.write(this.hairNum);
				System.out.println(this.id + "号顾客发现有座，他选择了这个发型：【" + this.hairMenu.get(hairNum).split(",")[0] + "】，他开始坐在座位上等待理发师来理发");
			} catch (IOException e) {
				e.printStackTrace();
			}
			GlobalBarberShop.chairMutex.Notify();
			//一个客人进来，选座位，选发型。这时一个原子操作
			GlobalBarberShop.barberMutex.Wait();
			try {
				String[] str = this.hairMenu.get(CheckMoneypis.read()).split(",");
				System.out.println("选择了【" + str[0] + "】发型的" + this.id + "号顾客扫描二维码结账，消费了【" + str[1] + "】");
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				sleep(100);//为了防止管道一个读的人都没有导致管道关闭，sleep一下。
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}else {
			GlobalBarberShop.chairMutex.Notify();
			System.out.println(this.id + "号顾客发现屋里人满了，就走了");
			//leave
		}
	}

}
