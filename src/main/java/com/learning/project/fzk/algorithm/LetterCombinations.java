package com.learning.project.fzk.algorithm;

import java.util.*;

/**
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
 * <p>
 * 给出数字到字母的映射，与电话按键相同。注意 1 不对应任何字母。
 * <p>
 * author:fanzhoukai
 * 2019/8/6 22:58
 */
public class LetterCombinations {

	static Map<Character, Character[]> nums = new HashMap<>();

	static {
		Character[] c2 = new Character[]{'a', 'b', 'c'};
		Character[] c3 = new Character[]{'d', 'e', 'f'};
		Character[] c4 = new Character[]{'g', 'h', 'i'};
		Character[] c5 = new Character[]{'j', 'k', 'l'};
		Character[] c6 = new Character[]{'m', 'n', 'o'};
		Character[] c7 = new Character[]{'p', 'q', 'r', 's'};
		Character[] c8 = new Character[]{'t', 'u', 'v'};
		Character[] c9 = new Character[]{'w', 'x', 'y', 'z'};
		nums.put('2', c2);
		nums.put('3', c3);
		nums.put('4', c4);
		nums.put('5', c5);
		nums.put('6', c6);
		nums.put('7', c7);
		nums.put('8', c8);
		nums.put('9', c9);
	}

	/**
	 * 计算9键字符组合
	 */
	public static List<String> letterCombinations(String digits) {
		Character[][] values = new Character[digits.length()][];
		for (int i = 0; i < digits.length(); i++) {
			values[i] = nums.get(digits.charAt(i));
		}
		List<List<Object>> descartes = descartes(values);

		List<String> result = new ArrayList<>();
		for (List<Object> descarte : descartes) {
			StringBuilder sb = new StringBuilder();
			for (Object o : descarte) {
				sb.append(o);
			}
			result.add(sb.toString());
		}
		return result;
	}

	/**
	 * 计算笛卡尔积
	 */
	public static List<List<Object>> descartes(Object[][] values) {
		List<List<Object>> result = new ArrayList<>();
		// 构造每一位的进制数
		int[] bits = new int[values.length];
		for (int i = 0; i < values.length; i++) {
			bits[i] = values[i].length;
		}

		// 当前进行到的位数
		int[] curBits = new int[values.length];

		do {
			List<Object> innerList = new ArrayList<>();
			for (int i = 0; i < values.length; i++) {
				innerList.add(values[i][curBits[i]]);
			}
			result.add(innerList);
		} while (addOne(curBits, bits));
		return result;
	}

	/**
	 * 对curBits进行加1操作。
	 * 首先对尾数加1，若不超过bits对应位置上的值，则直接返回，否则执行进位操作。
	 * 进位是递归操作，当首位同样超过bits对应值后，说明加满了，返回false
	 *
	 * @param curBits 要加1的数组
	 * @param bits    标准数组，每一位表示curBits的最高位数，超过则进位
	 * @return
	 */
	public static boolean addOne(int[] curBits, int[] bits) {
		int len = curBits.length;
		if (len == 0) {
			return false;
		}
		// 当达到bits(进制)位数-1，就需要进位了
		if (curBits[len - 1] == bits[len - 1] - 1) {
			return addOne(curBits, bits, len - 2);
		}
		curBits[len - 1]++;
		return true;
	}

	// 在指定位上加1，并将后面全部置零
	private static boolean addOne(int[] curBits, int[] bits, int i) {
		// 加满了，返回false
		if (i < 0) {
			return false;
		}
		// 当前位不够，递归向前进位
		if (curBits[i] == bits[i] - 1) {
			return addOne(curBits, bits, i - 1);
		}
		// 在第i位上加1，并将后面值全部置0
		curBits[i]++;
		for (i++; i < curBits.length; i++) {
			curBits[i] = 0;
		}
		return true;
	}


	/**
	 * 没看懂的笛卡尔积算法
	 */
	public static List<List<String>> descartes_2(List<List<String>> dimvalue) {
		List<List<String>> result = new ArrayList<>();
		descartes(dimvalue, result, 0, new ArrayList<>());
		return result;
	}

	public static void descartes(List<List<String>> dimvalue, List<List<String>> result, int layer, List<String> curList) {
		if (layer < dimvalue.size() - 1) {
			if (dimvalue.get(layer).size() == 0) {
				descartes(dimvalue, result, layer + 1, curList);
			} else {
				for (int i = 0; i < dimvalue.get(layer).size(); i++) {
					List<String> list = new ArrayList<String>(curList);
					list.add(dimvalue.get(layer).get(i));
					descartes(dimvalue, result, layer + 1, list);
				}
			}
		} else if (layer == dimvalue.size() - 1) {
			if (dimvalue.get(layer).size() == 0) {
				result.add(curList);
			} else {
				for (int i = 0; i < dimvalue.get(layer).size(); i++) {
					List<String> list = new ArrayList<String>(curList);
					list.add(dimvalue.get(layer).get(i));
					result.add(list);
				}
			}
		}
	}

	/**
	 * 链表法
	 */
	public static List<String> letterCombinations_3(String digits) {
		LinkedList<String> result = new LinkedList<String>();
		if (digits.isEmpty())
			return result;
		String[] mapping = new String[]{"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

		// 添加初始元素，长度为0
		result.add("");

		// i表示处理到原始输入的第几位
		for (int i = 0; i < digits.length(); i++) {
			int inputNum = Character.getNumericValue(digits.charAt(i));
			while (result.peek().length() == i) {
				// 上一次循环中未经处理的元素
				String prev = result.removeFirst();
				// 添加当前输入的每一个可能的字母newChar
				for (char newChar : mapping[inputNum].toCharArray()) {
					result.add(prev + newChar);
				}
			}
		}

		return result;
	}
}
