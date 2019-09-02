package com.learning.project.gfq.pvOperation.readWrite;

import com.learning.project.gfq.pvOperation.Signal;

public class GlobalReadWrite {
	
	public static Signal RWMutex = new Signal(1);
	public static Signal RCMutex = new Signal(1);
	
	public static int rCount = 0;
	
	public static String BUFFER = new String("0");

}
