package com.learning.project.gfq.pvOperation.barberShop;

import com.learning.project.gfq.pvOperation.Signal;


public class GlobalBarberShop {
	
	public static Signal chairMutex = new Signal(1);
	public static Signal barberMutex = new Signal(0);
	public static Signal customerMutex = new Signal(0);
	
	public static int chairNum = 10;

//	public Map<Integer, String> hashMap = new HashMap<Integer, String>(){
//		{
//			hashMap.put(1, "1");
//			hashMap.put(1, "1");
//		}
//	};

}
