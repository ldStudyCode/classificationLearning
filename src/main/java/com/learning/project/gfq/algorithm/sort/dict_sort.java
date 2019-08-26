package com.learning.project.gfq.algorithm.sort;

import java.io.IOException;
import java.util.Arrays;

public class dict_sort {

	public static void main(String[] args) throws IOException {
		
		
		System.out.println("input:87654312");
		System.out.println("output:" + prac1(87654312));
		
		String[] ins = {"abvds","adwasd","adsawd","dfawadwad"};
		System.out.println(TopNoK(1,ins));
		

		
	}
	

	
	
	
	// --寻找基于字典序的下一个排序-----------------------------------------------------------------------------------
	/**
	 * 寻找基于字典序的下一个排序
	 * 输入：358764
	 * 输出：364578
	 * @param num
	 * @return
	 */
	public static int prac1 (int num) {
		char[] chars = String.valueOf(num).toCharArray();
		int needToBeChangedPosition = 0;
		char needToBeChangedChar = '0';
		int theReplacerPosition = 0;
		char theReplacerChar = '0';
		
		for(int i = chars.length-1; i >= 0; i --) {
			if(Integer.valueOf(chars[i]) < needToBeChangedChar) {
				//遇到第一个点了
				needToBeChangedPosition = i;
				needToBeChangedChar = chars[i];
				break;
			}else {
				needToBeChangedChar = chars[i];
				if(i == 0) {//循环到最后一位还没有遇到，说明这个组合式最大的，需要链到开头
					
					return fanzhuan(num);
				}
			}
		}
		for(int i = chars.length-1; i >= 0; i --) {
			theReplacerChar = chars[i];
			if(theReplacerChar > needToBeChangedChar) {
				//遇到第二个点了
				theReplacerPosition = i;
				theReplacerChar = chars[i];
				break;
			}
		}
		chars[needToBeChangedPosition] = theReplacerChar;
		chars[theReplacerPosition] = needToBeChangedChar;
		char[] charLeft = Arrays.copyOfRange(chars, 0, needToBeChangedPosition + 1);
		char[] charRight = Arrays.copyOfRange(chars, needToBeChangedPosition + 1, chars.length);
		int charRightNum = Integer.valueOf(String.valueOf(charRight));
		charRightNum = fanzhuan (charRightNum);
		charRight = String.valueOf(charRightNum).toCharArray();
		System.arraycopy(charLeft, 0, chars, 0, charLeft.length);
		System.arraycopy(charRight, 0, chars, 2, charRight.length);
		return Integer.valueOf(String.valueOf(chars));
	}
	
	/**
	 * 反转数字
	 * 输入：1234
	 * 输出：4321
	 * @param num
	 * @return
	 */
	public static int fanzhuan (int num) {
		int i;
	    int num1=0;
		while(num>0){
	        //取当前num值的个位数。
	        i = num % 10;
	        //取num去掉个位数后的数字，如123变成12.
	        num = num / 10;
	        num1 = num1 * 10 + i;
	    }
		return num1;
	}
	
	
	// --一个乱序数组，求第K大的数。排序方式使用字典序---------------------------------------------------------------------
	
	public static String TopNoK(int k, String[] arry) {
		arry = sort(arry);
		if(k <= arry.length){
			return arry[k-1];
		}else {
			return null;
		}
	}
	
	/**
	 * 冒泡排序
	 * @param ins
	 * @return
	 */
	public static String[] sort(String[] ins){
		boolean flag = true;		
			for(int i=ins.length-1; i>0; i--){
				for(int j=0; j<i; j++){//每次到达最后一个i下标的前一个，然后和后一个比较
					if(leftBiggerThanRight(ins[j], ins[j+1]) == -1){
						String temp = ins[j+1];
						ins[j+1] = ins[j];
						ins[j] = temp;
					}
				}
			}
		return ins;	
	}
	
	/**
	 * 左边  >  右边，则返回1
	 * 左边  == 右边，则返回0
	 * 左边  <  右边，则返回-1
	 * @param left
	 * @param right
	 * @return
	 */
	public static int leftBiggerThanRight(String left, String right) {
		char[] leftChars = left.toCharArray();
		char[] rightChars = right.toCharArray();
		
		/*
		 * 第一位代表循环长度，第二位代表左右谁更短
		 * 第二位取值：
		 * -1：左更短
		 *  0：一样长
		 *  1：右更短
		*/
		int[] length_lORr = {0,0};
		if(leftChars.length < rightChars.length) {
			length_lORr[0] = leftChars.length;
			length_lORr[1] = -1;
		}else if(leftChars.length == rightChars.length) {
			length_lORr[0] = rightChars.length;
			length_lORr[1] = 0;
		}else {
			length_lORr[0] = rightChars.length;
			length_lORr[1] = 1;
		}
		for(int i = 0; i < length_lORr[0]; i++) {
			if(Integer.valueOf(leftChars[i]) == Integer.valueOf(rightChars[i])){
				continue;
			}else if(Integer.valueOf(leftChars[i]) > Integer.valueOf(rightChars[i])) {
				return 1;
			}else if(Integer.valueOf(leftChars[i]) < Integer.valueOf(rightChars[i])) {
				return -1;
			}
		}
		if(length_lORr[1] == -1) {//左边的短，两个前几位一样，说明右边的大
			return -1;
		}else if(length_lORr[1] == 0) {
			return 0;
		}else {
			return 1;
		}
	}
	
	
	
}



















