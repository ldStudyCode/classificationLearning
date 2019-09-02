package com.learning.project.gfq.pvOperation.barberShop;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Barber extends Thread {
	
	int id = 0;
	PipedOutputStream CheckMoneypos;//发送付款二维码管道
	PipedInputStream SelectHairpis;//接收发型管道
	
	public final Map<Integer, String> hairMenu = new HashMap<Integer, String>();
	
	public Barber(int id, PipedOutputStream CheckMoneypos, PipedInputStream SelectHairpis) {
		this.CheckMoneypos = CheckMoneypos;
		this.SelectHairpis =SelectHairpis;
	}
	
	@Override
	public void run() {
		hairMenu.put(0, "卷发,10元");
		hairMenu.put(1, "光头,15元");
		hairMenu.put(2, "黑长直,100元");
		
		while(!isInterrupted()){
			GlobalBarberShop.customerMutex.Wait();
			GlobalBarberShop.chairMutex.Wait();
			GlobalBarberShop.chairNum++;
			GlobalBarberShop.chairMutex.Notify();
			int hairNum = 0;
			try {
				hairNum = SelectHairpis.read();
				System.out.println(this.id + "号理发师开始处理【" + this.hairMenu.get(hairNum).split(",")[0] + "】");
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				CheckMoneypos.write(hairNum);
				System.out.println(this.id + "号理发师理好了【" + this.hairMenu.get(hairNum).split(",")[0] + "】，发送付款【" + this.hairMenu.get(hairNum).split(",")[1] + "】二维码"); 
			} catch (IOException e) {
				e.printStackTrace();
			}
			GlobalBarberShop.barberMutex.Notify();
		}
	}
}
