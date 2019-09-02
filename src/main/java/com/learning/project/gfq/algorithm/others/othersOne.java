package com.learning.project.gfq.algorithm.others;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.learning.project.gfq.commonJava.Node;

public class othersOne {

	public static void main(String[] args) {
		
		
//		System.out.println(prac2());
		
//		int[] prac3ins = {-1,0,1,2,5};
//		System.out.println(prac3(prac3ins));
		
//		LinkedList<String> linkedList = new LinkedList<String>();
//		linkedList.add("A");
//		linkedList.add("B");
//		linkedList.add("C");
//		linkedList.add("D");
//		System.out.println(linkedList);
//		System.out.println(prac3_1(linkedList));
		
//		Solution_Week2 nodePrac3_2 = new Solution_Week2();
//		//外部类.内部类 内部类对象 = 外部类实例.new 内部类();
//		Solution_Week2.SimpleNode node = nodePrac3_2.new SimpleNode("a");
//		for(int i = 0; i < 10; i++) {
//			node.add(nodePrac3_2.new SimpleNode(String.valueOf(i)));
//		}
//		prac3_2(node).print();
		
//		System.out.println(prac4());
		
//		System.out.println(prac5_1(12321));
//		System.out.println(prac5_2("ABCBA"));
		
//		System.out.println(prac6("zzz","zzzz"));
		
//		int[] arry = {4,71,323,42,1,4,5,2,1,4,42,23,23,1,2,5,8,0};
//		prac7(arry);
//		System.out.println(Arrays.toString(arry));
		
//		System.out.println(add("134t4re43t", "45tg45gtt5t5t4tv3eq"));
//		System.out.println(prac6_rewrite("aaadawdasdawd","1aaa11"));
		
//		int[] arry = {4,71,323,42,1,4,5,2,1,4,42,23,23,1,2,5,8,0};
//		int[] arry = {1,3,6,8,1,4,5,7};
//		prac7_chongxie(arry);
//		System.out.println(Arrays.toString(arry));
//		List<Integer> res = prac2_threadPool(2000);
//		System.out.println("number is :" + res.size());
//		for(Integer i: res){
//			System.out.print(i + ",");
//		}

//		Node node = new Node("1");
//		node.add(new Node("2"));
//		node.add(new Node("3"));
//		node.add(new Node("4"));
//		node.add(new Node("5"));
//		node.add(new Node("6"));
//		node.add(new Node("7"));
//		node.add(new Node("8"));
//		node = deleteLastN(node, 0);
//		node.print();

//		Set<Integer[]> set = subArray(arry);
//		System.out.println("总个数：" + set.size());
//		for(Integer[] tmp: set) {
//			System.out.println(Arrays.toString(tmp));
//		}
//		int[] arry = {2,2,3,1,2,3,1,2,3,4,2,42,3,3,3,2,1,2};
//		System.out.println(Arrays.toString(reduceWeight(arry)));
	}
	

	/**
	 * 数组去重
	 * @param arry
	 * @return
	 */
	public static Integer[] reduceWeight(int[] arry) {
		Set<Integer> set = new HashSet<Integer>();
		for(int i = 0; i < arry.length; i++) {
			set.add(arry[i]);
		}
		
		Integer[] res = new Integer[set.size()];
		res = set.toArray(res);
		
		return res;
	}
	

	
	//-求1~2000的所有素数.有足够的内存,要求尽量快----------------------------
	public static String prac2() {
		StringBuffer res = new StringBuffer();
		res.append("2,");
		for(int i = 1; i <= 1000; i++) {//循环1-2000内的奇数
			int odd = 2*i + 1;
			if(isPrime(odd)) {
				res.append(odd + ",");
			}
		}
		return res.substring(0, res.length()-1);
	}
	
	public static List<Integer> prac2_threadPool(final int limit) {
		final List<Integer> res = new CopyOnWriteArrayList<Integer>();
		
		//创建线程池
		final int poolSize = 5;
		ExecutorService pool = Executors.newFixedThreadPool(poolSize); 
		//线程计时器
		final CountDownLatch countDown = new CountDownLatch(poolSize);
		
		for(int i = 2; i < 2 + poolSize; i++){
			final int init = i;
			pool.execute(new Runnable() {
				@Override
				public void run() {
					for(int j = init; j <= limit; j +=  poolSize) {
						if(isPrime(j)) {
							res.add(j);
						}
					}
					countDown.countDown();
				}
			});
		}
		try {
			countDown.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		pool.shutdown();
		return res;
	}
	
	/**
	 * 判断是否为素数
	 * @param num
	 * @return
	 */
	public static boolean isPrime(int num) {
		
		if(num == 1){
			return true;
		}
		double numSqrt = Math.sqrt(num);
		if((int)numSqrt == numSqrt) {//若平方根为整数，则不是素数
			return false;
		}
		for(int i = 2; i <= (int)numSqrt; i++) {//遍历2 -(int)numSqrt之间的数
			float tmp = (float)num / i;
			if((int)tmp == tmp) {
				return false;
			}
		}
		return true;
	}
	
	
	//问题出在两个一起移动了，要一个移动另一个不移动才行
	//  --给你一个有序整数数组，数组中的数可以是正数、负数、零，请实现一个函数，这个函数返回一个整数：返回这个数组所有数的平方值中有多少种不同的取值-------------------------------------------------
	public static int prac3(int[] arry) {
		int left = 0;
		int right = arry.length -1;
		int resCount = 0;
		if(left == right){
			return 1;
		}else if(left > right) {
			return 0;
		}
		while(left <= right) {
			if(prac3_leftBiggerThanRight(arry[left], arry[right]) == 1) {
				resCount ++;
				left++;
				continue;
			}else if(prac3_leftBiggerThanRight(arry[left], arry[right]) == 0) {
				resCount ++;
				left++;
				right--;
				continue;
			}else {
				resCount ++;
				right--;
				continue;
			}
		}
		return resCount;
	}
	
	
	/**
	 * 返回值：
	 * 左  > 右，返回：1
	 * 左  = 右，返回：0
	 * 左  < 右，返回：-1
	 * @param left
	 * @param right
	 * @param left_right
	 * @return
	 */
	public static int prac3_leftBiggerThanRight(int left, int right) {
		if(Math.abs(left) > Math.abs(right)) {
			return 1;
		}else if (Math.abs(left) == Math.abs(right)) {
			return 0;
		}else {
			return -1;
		}
	}
	

	
	// -已知 sqrt (2)约等于 1.414，要求不用数学库，求 sqrt (2)精确到小数点后 10 位-------------------------
	public static double prac4() {
		int num = 2;
		double lower = 1.414;
		double higher = 1.415;
		
		while(true) {
			if((higher + lower)/2*(higher + lower)/2 > num) {
				higher = (higher + lower)/2;
			}else if((higher + lower)/2*(higher + lower)/2 < num) {
				lower = (higher + lower)/2;
			}
			double res = (higher + lower)/2*(higher + lower)/2 - num;
			if(res> 0 && res < 0.0000000001) {
				break;
			}else if(res < 0 && res> -0.0000000001) {
				break;
			}
		}
		return (higher + lower)/2;
	}
	
	
	// -判断一个数字是否是回文数-------------------------
	//解1，纯数字输入下的最佳解
	public static boolean prac5_1(int num) {
		int tmpMid = 0;
		int tmpNum = 0;
		int newNum = 0;
		while(true) {
			tmpMid = num%10;
			tmpNum = num / 10;
			newNum = newNum + tmpMid;
			if(newNum == num || newNum == tmpNum) {
				return true;
			}else if(newNum > tmpNum) {
				break;
			}else {
				num = tmpNum;
				newNum = newNum*10;
			}
		}
		return false;
	}
	//解2，字符串输入下的解
	public static boolean  prac5_2(String str) {
		char[] strChars = str.toCharArray();
		int left = 0;
		int right = strChars.length - 1;
		while(left <= right) {
			if(strChars[left++] != strChars[right--]){
				return false;
			}
		}
		return true;
	}
	
	

	
	
	
	//删除倒数第N个节点 N >= 1
	public static Node deleteLastN(Node node, int N) {
		if(N < 1) {
			return node;
		}
		int step = N - 1;
		Node current = node;
		Node left = node;//存放左指针的左边第一个结点，此结点的下一个节点是左指针应指向的结点
		//第一个指针先行多少步
		for(int i = step; i > 0; i--) {
			if(null != current.next) {
				current = current.next;
			}else {
				return node;//N > node.length 没得删；
			}
		}
		//多向后移动一个，让left节点记录注释描述的那个node
		if(null != current.next) {
			current = current.next;
		}else {
			return node.next;//若此时不可移动，则正好要删除第一个头节点
		}
		while(null != current.next) {
			current = current.next;
			left = left.next;
		}
		left.next = left.next.next;
		return node;
	}
	
	
	//求不重复数组的全部子集
	/*
	 * 1、list可以转数组
	 * 2、利用了二进制操作的特点，创建单位矩阵，求与操作，来确定下标
	*/
//	List<Integer> list = new ArrayList<Integer>();
//	list.add(1);
//	list.add(2);
//	list.add(3);
//	Integer[] a = (Integer[])list.toArray();
	public static Set<Integer[]> subArray(int[] arry) {
		Set<Integer[]> res = new HashSet<Integer[]>();
		
		int length = arry.length;
		int[] Ematrix = new int[length];
		//初始化单位矩阵
		for(int i = 0; i < length; i++) {
			Ematrix[i] = (int) Math.pow(2, i);
		}
		int number = (int)Math.pow(2, length) - 1;
		while(number > 0) {//初始值为全1，所以应先--操作
			number--;
			List<Integer> list = new ArrayList<Integer>();
			for(int i = 0; i < length - 1; i++) {
				if((Ematrix[i] & number) > 0) {
					list.add(arry[i]);
				}
			}
			res.add(list.toArray(new Integer[list.size()]));
		}
		return res;
	}
	
	// -找出数组中出现次数超过一半的数，现在有一个数组，已知一个数出现的次数超过了一半，请用O(n)的复杂度的算法找出这个数
	// 笨方法，使用map集合存储
	public static int prac9_findNum(int[] arry) {
		int res = -1;
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for(int i = 0; i < arry.length; i++) {
			if(map.containsKey(arry[i])) {
				int count = map.get(arry[i]);
				count++;
				if(count > arry.length/2) {
					return arry[i];
				}else {
					map.put(arry[i], count);
				}
			}else {
				map.put(arry[i], 1);
			}
		}
		return res;
	}
	
	//利用中位数的方法，可先对数组排序，其中位数就是出现次数超过半数的值
	//找一个排序算法，当数组中出现很多相同数值对这个排序算法很有利，即可
	//随机快排的方法感觉不成立啊，应当如何做到O（n）的方式找到中位数呢
	/*
	 * 预备：
	 * 中位数定义：
	 * 如果数据的个数是奇数，则中间那个数据就是这群数据的中位数
	 * 如果数据的个数是偶数，则中间那2个数据的算术平均值就是这群数据的中位数
	*/
	// -在一个乱序的数组中找出中位数
	// 快排的二分法只能解决奇数个数据的数组找中位数的问题，偶数个数据的数组无法找
	/*
	 * 小细节：
	 * 	现象：想传进方法中一个Integer参数，向外出结果，但是方法内参数值改变，方法外值不变
	 * 	解释：方法内生成的新Integer是一个新对象，这样的赋值操作会把对象指针覆盖，因而无法改变外面的对象值，需要像list.add的方式进行赋值才可以
	*/
	public static int prac10_findMidNum(int[] arry) {
		int res = findMid(arry, 0, arry.length - 1);
		return res;
	}
	public static int findMid(int[] arry, int left, int right) {
		int res = 0;
		int l = left;
		int r = right;
		while(l < r) {
			while(arry[r] > arry[left] && l < r) {
				r--;
			}
			while(arry[l] <= arry[left] && l < r) {
				l++;
			}
			int tmp = arry[l];
			arry[l] = arry[r];
			arry[r] = tmp;
		}
		int tmp = arry[l];
		arry[l] = arry[left];
		arry[left] = tmp;
		//偶数的处理
		if(arry.length%2 == 0 && l > arry.length/2) {//左侧是数组中有结果,注意此处比较，值相差1
			res = findMid(arry, left, l-1);
		}else if(arry.length%2 == 0 && l < arry.length/2 - 1) {//注意此处比较，值相差1
			res = findMid(arry, l+1, right);
		}else if(arry.length%2 == 0 && l == arry.length/2) {//注意此处比较，值相差1
			res = arry[l];
			int tmpR = findMaxOrMin(arry, left, l-1, true);
			res = (res + tmpR)/2;
		}else if(arry.length%2 == 0 && l == arry.length/2 - 1) {//注意此处比较，值相差1
			res = arry[l];
			int tmpR = findMaxOrMin(arry, l+1, right, false);
			res = (res + tmpR)/2;
		}
		//奇数的处理
		if(arry.length%2 != 0 && l > arry.length/2) {//注意此处比较，值相差1
			res = findMid(arry, left, l-1);
		}else if(arry.length%2 != 0 && l < arry.length/2) {//注意此处比较，值相差1
			res = findMid(arry, l+1, right);
		}else if(arry.length%2 != 0 && l == arry.length/2) {//注意此处比较，值相差1
			res = arry[l];
		}
		return res;
	}
	
	public static int findMaxOrMin(int[] arry, int left, int right, boolean findMax) {
		int res = arry[left];
		for(int i = left; i <= right; i++) {
			if(findMax) {
				res = arry[i] > res?arry[i]:res;
			}else {
				res = arry[i] < res?arry[i]:res;
			}
		}
		return res;
	}
	
	// -实现一个函数，对一个正整数n，算得到1需要的最少操作次数。操作规则为：如果n为偶数，将其除以2；如果n为奇数，可以加1或减1；一直处理下去
	public static int prac6_toOneTime(int num) {
		System.out.println(Integer.toBinaryString(num));
		int res = 0;
		String str = new String(Integer.toBinaryString(num));
		char[] chars = str.toCharArray();
		res = res + chars.length - 1;
		int oneCount = 0;
		//从低位开始，按位扫描
		for(int i = chars.length - 1; i >= 0; i--) {
			/*
			 * 扫描到1时：
			 * 	count计数器+1
			 * 	if(扫描到的是最后一位)
			 * 		if(此时有2个1)
			 * 			+1
			 * 		elseif(此时有3个以上的1)
			 * 			+2
			*/
			if(chars[i] == '1') {
				oneCount++;
				if(i == 0) {//最高位是1
					if(oneCount == 2) {
						res++;
					}else if(oneCount >= 3) {
						res = res + 2;
					}
				}
			/*
			 * 扫描到0时：
			 * 	if(之前的1多于1个)
			 * 		if(往前看1位，是否是1)
			 * 			是，则oneCount置1，即当前位的0看作1，且res++，记录进行了一步+1操作的消耗
			 *		else
			 *			否则，按res + 2的逻辑记录加法操作消耗，并oneCount置零
			 *	elseif(之前的1只有1个)
			 *		按减操作记录消耗，并oneCount置零
			 * 			
			*/
			}else if(chars[i] == '0') {
				if(oneCount > 1) {
					if(i-1 >= 0  && chars[i-1] == '1') {//向前看一位
						oneCount = 1;
						res++;//按加法进位的操作记录一半消耗，另一半消耗化作oneCount的1
					}else {
						res = res + 2;//按加法操作记录消耗
						oneCount = 0;
					}
				}else if(oneCount == 1) {
					res++;//按减法操作记录消耗
					oneCount = 0;
				}
			}
		}
		return res;
	}
	
	//给定一个整数数组和一个整数，返回两个数组的索引，这两个索引指向的数字的加和等于指定的整数。需要最优的算法，分析算法的空间和时间复杂度
	public static List<Integer[]> prac4_getIndex(int[] arry, int target) {
		List<Integer[]> list = new ArrayList<Integer[]>();
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for(int i = 0; i < arry.length; i++) {
			int sub = target - arry[i];
			if(map.containsKey(sub)) {
				Integer[] res = new Integer[2];
				res[0] = map.get(sub);
				res[1] = i;
				list.add(res);
			}else {
				map.put(arry[i], i);
			}
		}
		return list;
	}
	
	// -已知sqrt(2)约等于1.414，要求不用数学库，求sqrt(2)精确到小数点后10位
	// 二分法
	public static double prac2_sqrt(int num) {
		double sqrtNumHigh = 1.415;
		double sqrtNumLow = 1.414;
		double sqrtNumMid = (sqrtNumHigh + sqrtNumLow)/2;
		double multi = sqrtNumMid * sqrtNumMid;
		while(multi - 2 > 0.0000000001 || multi - 2 <  -0.0000000001) {
			if(multi > 2) {
				sqrtNumHigh = sqrtNumMid;
			}else {
				sqrtNumLow = sqrtNumMid;
			}
			sqrtNumMid = (sqrtNumHigh + sqrtNumLow)/2;
			multi = sqrtNumMid * sqrtNumMid;
		}
		return sqrtNumMid;
	}
	
	
}





































