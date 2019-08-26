package com.learning.project.gfq.pvOperation.readWrite;

public class Reader extends Thread{
	
	int id = 0;
	
	public Reader(int id) {
		this.id = id;
	}

	@Override
	public void run() {
		while(!isInterrupted()) {
			GlobalReadWrite.RCMutex.Wait();
			if(GlobalReadWrite.rCount == 0) {
				GlobalReadWrite.RWMutex.Wait();
			}
			GlobalReadWrite.rCount++;
			GlobalReadWrite.RCMutex.Notify();
			//=====================
			System.out.println("读者" + this.id + "读取结果：" + GlobalReadWrite.BUFFER);
			//=====================
			GlobalReadWrite.RCMutex.Wait();
			GlobalReadWrite.rCount--;
			if(GlobalReadWrite.rCount == 0) {
				GlobalReadWrite.RWMutex.Notify();
			}
			GlobalReadWrite.RCMutex.Notify();
			try {
				this.sleep((long)400);
			} catch (InterruptedException e) {
				break;
			}
		}
	}

}
