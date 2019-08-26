package com.learning.project.gfq.algorithm.string;

import java.util.HashMap;

public class StringOne {
	
	public static void main(String[] args) {
		System.out.println(prac1("abcd"));
		
		System.out.println(prac6("zzz","zzzz"));
		System.out.println(prac6_rewrite("aaadawdasdawd","1aaa11"));
		
		
	}
		

	
	// -实现一个函数，把一个字符串中的字符从小写转为大写----------------------------
	public static String prac1(String input) {
		int degree = (int)'a' - (int)'A';
		char[] arry = input.toCharArray();
		for(int i = 0; i <arry.length; i++) {
			arry[i] = (char)((int)arry[i] - degree);
		}
		return String.valueOf(arry);
	}
	
	//-字符串加法。字符串由0-9、a-z共36个数字组成，计算两个字符串之和，36进制的加法---------------------
	public static final int LENGTH_LEFT_SHORTER = -1;
	public static final int LENGTH_EQUAL = 0;
	public static final int LENGTH_RIGHT_SHORTER = 1;
	
	public static String prac6(String str1, String str2) {
		
		char[] str1Chars = str1.toCharArray();
		char[] str2Chars = str2.toCharArray();
		char[] resChar = null;
		int char1Lenght = str1Chars.length;
		int char2Lenght = str2Chars.length;
		int shortLenght = 0;
		int lengthStatus = 9;
		if(char1Lenght < char2Lenght) {
			shortLenght = char1Lenght;
			resChar = new char[char2Lenght+1];
			lengthStatus = LENGTH_LEFT_SHORTER;
		}else if(char1Lenght == char2Lenght) {
			shortLenght = char1Lenght;
			resChar = new char[char2Lenght+1];
			lengthStatus = LENGTH_EQUAL;
		}else {
			shortLenght = char2Lenght;
			resChar = new char[char1Lenght+1];
			lengthStatus = LENGTH_RIGHT_SHORTER;
		}
		
		
		HashMap<String, String> eachCharResult = null;
		int carryNum = 0;
		for(int i = shortLenght-1; i >= 0; i--){//i仅标记循环次数
			eachCharResult = addTwoChar(str1Chars[char1Lenght-1], str2Chars[char2Lenght-1],carryNum);
			carryNum = Integer.valueOf(eachCharResult.get(CARRY_NUM));
			if(lengthStatus == LENGTH_LEFT_SHORTER || lengthStatus == LENGTH_RIGHT_SHORTER) {
				resChar[char1Lenght > char2Lenght?(char1Lenght):(char2Lenght)] = eachCharResult.get(RESULT).charAt(0);
			}else if(lengthStatus == LENGTH_EQUAL) {
				resChar[char1Lenght] = eachCharResult.get(RESULT).charAt(0);
			}
			char1Lenght --;
			char2Lenght --;
			int leftOver = char1Lenght > char2Lenght?char1Lenght:char2Lenght;
			if(i == 0 && (carryNum != 0 || leftOver > 0)) {
				if(leftOver == 0) {
					resChar[0] = String.valueOf(carryNum).charAt(0);
				}else {
					for(int j = leftOver-1; j >= 0; j--) {
						switch(lengthStatus){
							case LENGTH_LEFT_SHORTER:
								eachCharResult = addTwoChar(str2Chars[char2Lenght-1], '0',carryNum);
								resChar[char2Lenght] = eachCharResult.get(RESULT).charAt(0);
								carryNum = Integer.valueOf(eachCharResult.get(CARRY_NUM));
								char2Lenght--;
								break;
							case LENGTH_RIGHT_SHORTER:
								eachCharResult = addTwoChar(str1Chars[char1Lenght-1], '0',carryNum);
								resChar[char1Lenght] = eachCharResult.get(RESULT).charAt(0);
								carryNum = Integer.valueOf(eachCharResult.get(CARRY_NUM));
								char1Lenght--;
								break;
						}
						if(j == 0 && carryNum != 0) {
							resChar[0] = String.valueOf(carryNum).charAt(0);
						}
					}
				}
			}
		}
		return String.valueOf(resChar);
	}
	
	public static final int NUM_BASE = 36;
	
	public static final String CARRY_NUM = "CARRY_NUM";
	
	public static final String RESULT = "RESULT";
	
	
	public static HashMap<String, String> addTwoChar(char char1, char char2,int carryNum) {
		String standard = "0123456789abcdefghijklmnopqrstuvwxyz";
		char[] syandardChars = standard.toCharArray();
		HashMap<String, String> resMap = new HashMap<String, String>();
		int char1Num = standard.indexOf(char1);
		int char2Num = standard.indexOf(char2);
		int sum = char1Num + char2Num + carryNum;
		resMap.put(CARRY_NUM, String.valueOf(sum / NUM_BASE));
		resMap.put(RESULT, String.valueOf(syandardChars[sum % NUM_BASE]));
		return resMap;
	}
	
	//解2：FZK
	private static String bits = "0123456789abcdefghijklmnopqrstuvwxyz";
	
	/*
	 * 优点：
	 * 	1、嘴上说的加法器思路是什么样的，代码上就是如何实现的，代码读起来会很顺。
	 * 	2、StringBuffer有一个reverse()方法，可以将内容反转。
	 * 	3、用StringBuffer解决了两个数组不等长的时候有可能出现的进位问题，因此代码简洁了。
	*/
	private static String add(String a, String b) {
		StringBuilder result = new StringBuilder();
		int i = a.length() - 1, j = b.length() - 1; // 对a,b的指针，从最后一位开始扫描
		// 是否进位，1表示需要，0为不需要
		int addOne = 0;
		while (i >= 0 || j >= 0 || addOne == 1) {
			int aCurr = 0, bCurr = 0;
			// 取到当前位置字符对应的数字
			if (i >= 0) {
				aCurr = bits.indexOf(a.charAt(i--));
			}
			if (j >= 0) {
				bCurr = bits.indexOf(b.charAt(j--));
			}
			// 判断是否需要进位
			int sumCurr = aCurr + bCurr + addOne;
			if (sumCurr >= 36) {
				result.append(bits.charAt(sumCurr - 36));
				addOne = 1;
			} else {
				result.append(bits.charAt(sumCurr));
				addOne = 0;
			}
		}
		return result.reverse().toString();
	}
	
	
	//自己仿写FZK代码：
	public static String prac6_rewrite(String a, String b) {
		StringBuffer resStr = new StringBuffer();
		 int len_a = a.length() - 1;
		 int len_b = b.length() - 1;
		 int carryNum = 0;
		 while(len_a >= 0 || len_b >=0 || carryNum >0) {
			 int num_a = len_a >= 0?bits.indexOf(a.charAt(len_a)):0;
			 int num_b = len_b >= 0?bits.indexOf(b.charAt(len_b)):0;
			 int sum = num_a + num_b + carryNum;
			 if(sum >= 36) {
				 carryNum  = sum / 36;
			 }else {
				 carryNum = 0;
			 }
			 sum = sum % 36;
			 resStr.append(bits.charAt(sum));
			 len_a --;
			 len_b --;
		 }
		return resStr.reverse().toString();
	}
	

}
