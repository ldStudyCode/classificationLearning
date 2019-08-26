package com.learning.project.gfq.pvOperation.pvExample;

import com.learning.project.gfq.pvOperation.Signal;

public class GlobalpvExample {
	
	public static Signal empty = new Signal(8);
	public static Signal full = new Signal();
	
	public static Signal pMutex = new Signal(1);
	public static Signal cMutex = new Signal(1);
	
	public static int pCount = 0;
	public static int cCount = 0;
	
	public static int[] BUFFER = new int[8];

}
