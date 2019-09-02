package com.learning.project.gfq.pvOperation.pvExample;

public class Consumer implements Runnable{
	
	int id = 0;
	
	public Consumer(int id) {
		this.id = id;
	}

	@Override
	public void run() {
		while(GlobalpvExample.cCount < 1000) {
			GlobalpvExample.full.Wait();
			GlobalpvExample.cMutex.Wait();
			//===========================
			int index = GlobalpvExample.cCount%8;
			int value = GlobalpvExample.BUFFER[index];
			GlobalpvExample.BUFFER[index] = 0;
			System.out.println("消费者" + this.id + "在缓冲区：" + index + "中消费了物品" + value);
			GlobalpvExample.cCount++;
			//===========================
			GlobalpvExample.cMutex.Notify();
			GlobalpvExample.empty.Notify();
		}
		
	}
	
	
	
}
