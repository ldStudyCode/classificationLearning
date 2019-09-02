package com.learning.project.gfq.pvOperation.pvExample;

public class Producer implements Runnable{
	
	int id = 0;
	
	public Producer(int id) {
		this.id = id;
	}
	
	@Override
	public void run() {
		
		while(GlobalpvExample.pCount < 1000) {
			GlobalpvExample.empty.Wait();
			GlobalpvExample.pMutex.Wait();
			//==========================
			int index = GlobalpvExample.pCount%8;
			GlobalpvExample.BUFFER[index] = GlobalpvExample.pCount;
			System.out.println("生产者" + this.id + "在缓冲区" + index + "中产生了物品" + GlobalpvExample.pCount);
			GlobalpvExample.pCount++;
			//==========================
			GlobalpvExample.pMutex.Notify();
			GlobalpvExample.full.Notify();
		}
		
	}

	
}
