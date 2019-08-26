package com.learning.project.gfq.dynamicProgramming;

import java.io.IOException;

public class dynamicProgramming {
	
	public static void main(String[] args) throws IOException {
		
		// -挖金矿问题----------------------------------------------------
		//每个矿包含的金子数量
		int[] g = {400,500,200,300,350};
		//每个矿所需的人数
		int[] nw = {5,5,3,4,3};
		for(int a: prac_mine(5, 10, g, nw)){
			System.out.println(a );
		}
		
		// -投筛子问题-------------------------------------------------------------
		System.out.println(prac_dice(100));
		
		// -背包问题-----------------------------------------------
		//背包容量
		int vol = 8;
		//物品数量
		int num = 4;
		//每个物品所占空间
		int[] cap = {2,3,4,5};
		//每个物品的价值
		int[] val = {3,4,5,6};
		System.out.println(prac_bag(vol, num, cap, val));
	}
	
	
	// -挖金矿问题----------------------------------------------------
	/*
	 * 有一个国家发现了5座金矿，每座金矿的黄金储量不同，需要参与挖掘的工人数也不同。
	 * 参与挖矿工人的总数是10人。
	 * 每座金矿要么全挖，要么不挖，不能派出一半人挖取一半金矿。
	 * 要求用程序求解出，要想得到尽可能多的黄金，应该选择挖取哪几座金矿？
	 * 
	 * 矿山号	金子数量，所需人数
	 * 1	400，5
	 * 2	500，5
	 * 3	200，3
	 * 4	300，4
	 * 5	350，3	
	*/
	
	/**
	 * @param m，总的矿的数量,1开始
	 * @param w，总的worker的数量，1开始
	 * @param g，每个矿山所得金子的数量，下标0开始
	 * @param nw，每个矿山所需的worker的数量，下标0开始
	 * @return 要挖哪几座矿，返回值为g或nw的数组下标
	 * @return 一共最多的情况下能采多少金子
	 */
	public static int[] prac_mine(int m, int w, int[] g, int[] nw) {
		
		int[] resGold = new int[w];
		
		//处理直接触发【边界值】的情况
		if(m == 0) {
			return resGold;
		}else if (m == 1 && w < nw[m-1]) {
			return resGold;
		}else if(m ==1 && w >= nw[m-1]) {
			resGold[0] = g[m-1];
			return resGold;
		}
		
		//初始化【边界值】
		for(int i = 1; i <= w; i++) {
			if(i < nw[0]) {
				resGold[i-1] = 0;
			}else {
				resGold[i-1] = g[0];
			}
		}
		int[] tmp = new int[w];
		for(int numOfMine = 2; numOfMine <= m; numOfMine ++) {
			for(int i = 1; i <= w; i++) {
				if(i < nw[numOfMine-1]) {
					tmp[i-1] = resGold[i-1];
				}else {
					if(i-nw[numOfMine-1] -1 <0) {
						tmp[i-1] = Math.max(resGold[i-1], 0 + g[numOfMine-1]);
					}else {
						tmp[i-1] = Math.max(resGold[i-1], resGold[i-nw[numOfMine-1]-1] + g[numOfMine-1]);
					}
				}
			}
			resGold = tmp;
		}
		return resGold;
	}
	
	
	// -投筛子问题-------------------------------------------------------------
	//投筛子，每次得1-6，从1开始走到100，投多少走几步，问恰好走到100，有多少种投掷情况可以达到
	
	public static int prac_dice(int endUp) {
		int one = 1;
		int two = 2;
		int three = 4;
		int four = 8;
		int five = 16;
		int six = 32;
		int sum = 0;
		if(endUp <= 0) {
			return 0;
		}else if(endUp <= 6) {
			switch(endUp){
				case 1:
					return one;
				case 2:
					return two;
				case 3:
					return three;
				case 4:
					return four;
				case 5:
					return five;
				case 6:
					return six;
			}
		}
		for(int i = 7; i <= endUp; i++) {
			switch(i%6){
				case 1:
					sum = one + two + three + four + five + six;
					one = sum;
					break;
				case 2:
					sum = one + two + three + four + five + six;
					two = sum;
					break;
				case 3:
					sum = one + two + three + four + five + six;
					three = sum;
					break;
				case 4:
					sum = one + two + three + four + five + six;
					four = sum;
					break;
				case 5:
					sum = one + two + three + four + five + six;
					five = sum;
					break;
				case 0:
					sum = one + two + three + four + five + six;
					six = sum;
					break;
			}
		}
		return sum;
		
	}
	
	// -背包问题-----------------------------------------------
	//有n个物品，它们有各自的体积和价值，现有给定容量的背包，如何让背包里装入的物品具有最大的价值总和？
	public static int prac_bag(int vol, int num, int[] cap, int[] val) {
		if(num != cap.length || num != val.length) {
			return 0;
		}
		if(num == 0 || cap.length == 0 || val.length ==  0) {
			return 0;
		}
		if(num == 1) {
			if(vol < cap[0]) {
				return 0;
			}else {
				return val[0];
			}
		}
		int[] preRes = new int[vol];
		//初始化值
		for(int i = 1; i <= vol; i++) {
			if(i < cap[0]) {
				preRes[i-1] = 0;
			}else {
				preRes[i-1] = val[0];
			}
		}
		//按初始值进行每一个阶段的状态转换计算
		int[] currRes = new int[vol];
		for(int i = 1; i < num; i++) {
			for(int j = 1; j <= vol; j++) {
				if(j < cap[i]) {
					currRes[j-1] = preRes[j-1];
				}else {
					if(j - cap[i] - 1 < 0) {//此处的if判断解释:preRes存储的是vol从1-8的值。有一种vol为0的情况也需要用到，但是一用就数组越界了，因此需要分开判断，或者解决办法就是把preRes变成0-8的数组
						currRes[j-1] = Math.max(preRes[j-1], 0 + val[i]);
					}else
						currRes[j-1] = Math.max(preRes[j-1], preRes[j - cap[i] - 1] + val[i]);
				}
			}
			preRes = currRes;
		}
		int max = 0;
		for(int tmp : currRes) {
			if(tmp > max) {
				max = tmp;
			}
		}
		return max;
	}
	
	
}