package com.learning.project.gfq.pvOperation.readWrite;

public class Writer extends Thread{

	int id = 0;
	
	public Writer(int id) {
		this.id = id;
	}

	@Override
	public void run() {
		while(!isInterrupted()) {
			GlobalReadWrite.RWMutex.Wait();
			//====================
			System.out.println("写者" + this.id + "写入前：" + GlobalReadWrite.BUFFER);
			GlobalReadWrite.BUFFER = String.valueOf(Math.random());
			System.out.println("写者" + this.id + "写入后：" + GlobalReadWrite.BUFFER);
			//====================
			GlobalReadWrite.RWMutex.Notify();
			try {
				this.sleep((long)100);
			} catch (InterruptedException e) {
				break;
			}
		}
	}

}
