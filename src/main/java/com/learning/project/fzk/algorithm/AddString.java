package com.learning.project.fzk.algorithm;

/**
 * 字符串加法。字符串由0-9、a-z共36个数字组成，计算两个字符串之和，36进制的加法。
 * <p>
 * author:fanzhoukai
 * 2019/7/20 23:01
 */
public class AddString {
	public static void main(String[] args) {
		System.out.println(add("134t4re43t", "45tg45gtt5t5t4tv3eq"));
	}

	private static String bits = "0123456789abcdefghijklmnopqrstuvwxyz";

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
}
