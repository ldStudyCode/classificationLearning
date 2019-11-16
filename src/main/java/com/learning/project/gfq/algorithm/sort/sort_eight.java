package com.learning.project.gfq.algorithm.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class sort_eight {

	public static void main(String[] args) {
		
		int[] arry = {2,54,1,23,6,234,2,32,234,234,2352,42342,3243423,234,234,234,3,2,3,4};
		System.out.println(prac2_directly_insertion_sort(arry));
		System.out.println(prac2_binary_insertion_sort(arry));
		System.out.println(prac2_shell_sort(arry));
		System.out.println(prac2_shell_sort_2(arry));
		System.out.println(prac3_straight_select_sort(arry));
		System.out.println(prac3_heap_sort(arry));
		System.out.println(prac3_bubble_sort(arry));
		System.out.println(prac3_quick_sort(arry));
		System.out.println(prac4_merge_sort(arry));
		System.out.println(prac5_basic_sort(arry));
	}
	
	
	
	
	// -八大排序算法实现---------------------------------------------
	// 排序算法实现细节的一个重要点，中间值不保留数组值，保留数组的位置指针值
	// 特指内部排序，即所有的数都能放进内存的情况下，进行的排序
	// 注意两个次数：比较次数、交换次数【算法必须锱铢必较，不然前面优化的1%很可能被某个地方又吃掉了】
	
	//时间复杂度
	/*
	 * 1、取决于执行次数最多的语句，如当有若干个循环语句时，算法的时间复杂度是由嵌套层数最多的循环语句中最内层语句的频度f(n)决定的
	 * 2、如果算法的执行时间不随着问题规模n的增加而增长，即使算法中有上千条语句，其执行时间也不过是一个较大的常数。此类算法的时间复杂度是O(1)
	 * 3、算法的时间复杂度不仅仅依赖于问题的规模，有时还受其他参数和判断条件影响
	*/
	
	//prac2：直接插入排序，折半插入排序，希尔排序
	
	//直接插入排序
	/*
	1、说明：
		将一条记录插入一个已知有序的数组中，直至全部插入完毕。
	2、细节优化：
		可否优化交换次数：可，58行写 >，不要写>=，可减少交换次数
		可否优化比较次数：可，62行加上else break;
	3、复杂度分析：
	    时间复杂度：O（n^2）
	 	空间复杂度：O(1)
	4、其他：
		最坏情况：数组正好与排序方向逆序
		最好情况：数组正好有序，比较次数n^2,交换次数0
		数组越有序，排序速度越快
	*/
	public static String prac2_directly_insertion_sort(int[] arry) {
		if(arry.length < 2) {//鲁棒
			return Arrays.toString(arry);
		}
		for(int i = 1; i < arry.length; i++) {
			for(int j = i; j >= 1; j--) {
				if(arry[j-1] > arry[j]){
					int tmp = arry[j-1];
					arry[j-1] = arry[j];
					arry[j] = tmp;
				}
			}
		}
		return Arrays.toString(arry);
	}
	
	//折半插入排序
	/*
	1、说明：
		把找合适的位置这个动作，换成了二分查找，其余不变，相当于优化了"比较次数"
	2、细节优化：
		可否优化交换次数：特殊情况下可，可避免相等情况下的交换
		可否优化比较次数：可，95行可以加等于时的判断，写在最后一个else中更好
			二分查找只能在匹配相等的时候，才能有控制能力，所以可以在这里下手，帮助在提前匹配的时候有一些操作
	3、复杂度分析：
		时间复杂度：O(nlog2n)：1 - n/2； 2 - n/4； x - n/2^x；最坏情况下，长度变为1时才找到了对的人
		空间复杂度：O(1)
	4、其他：
		low、high、mid之间的关系：
		遇到拆分数组的时候，while循环最好用，记住两个点(2+2)/2=2;(2+3)/2=2，这样就记住了l <= h 和 h = m - 1；l = m + 1
			while循环适用于：在循环结束后，还要在用到循环内迭代修改的变量
			for循环适用于：在循环结束后，不需要用到循环内迭代修改的变量，代码更简洁
	*/
	//折半查找只是减少了比较次数，但是元素的移动次数不变
	public static String prac2_binary_insertion_sort(int[] arry) {
		if(arry.length < 2)
			return Arrays.toString(arry);
		for(int i = 1; i < arry.length; i++) {
			int low = 0;
			int high = i-1;
			//二分查找，找到合适的人
			while(low <= high) {
				int mid = (low + high)/2;//
				//int mid = low+(high-low)/2
				if(arry[i] < arry[mid]){
					high = mid - 1;
				}else {
					low = mid + 1;
				}
			}
			//交换
			int first = arry[low];
			arry[low] = arry[i];
			int tmp = 0;
			//移动
			while(low <= i - 1) {
				tmp = arry[low + 1];
				arry[low + 1] = first;
				first = tmp;
				low ++;
			}
		}
		return Arrays.toString(arry);
	}
	
	//希尔排序 shell sort （缩小增量排序）
	//https://blog.csdn.net/weixin_37818081/article/details/79202115
	//increment取法是关键
	/*
	1、说明：
		利用一个increment，将数组拆成小数组
		开始时，increment的取值较大，每个子序列中的元素较少，排序速度较快
		后期increment取值逐渐变小，子序列中元素个数逐渐增多，此时已基本有序，插入排序优势体现了
	2、细节优化：
		可否优化交换次数：同插入排序
		可否优化比较次数：同插入排序
		外面包的一层increment本人认为没有太多优化点
	3、复杂度分析：
		时间复杂度与increment相关，难以计算
		空间复杂度O(1)
	4、其他：
		increment取法是关键：
		关键点是，尽量让每一次increment变化时，没见过面的人能互相见面排一次序，那么increment取的就好
		如：shell最初提increment = increment/2,这样只有到最后，奇数才能与偶数碰面并比较
		两个说法 1、increment/3 2、increment取质数。
		Robert Sedgewick 编写了一种动态定义increment的算法，未明白用意，希望讨论一下数学层面，是否做到了每次让不同的人碰面，https://www.cnblogs.com/duhuo/p/5088975.html

	*/
	public static String prac2_shell_sort(int[] arry) {
		int increment = arry.length - 1;
		increment = increment / 3 + 1;
		while(increment >= 1) {
			for(int i = 0; i < increment; i++) {//i为每一步的起点
				//一下实现一个直接插入排序
				int j = i;
				do{
					int m = j;
					int tmp = arry[j];
					while(m -increment >= 0) {
						if(arry[m -increment] > arry[m]) {
							tmp = arry[m -increment];
							arry[m -increment] = arry[m];
							arry[m] = tmp;
						}
						m = m -increment;
					}
					j = j + increment;
				}while(j <= arry.length - 1);
			}
			if(increment != 1) {
				increment = increment / 3 + 1;
			}else {
				break;
			}
		}
		return Arrays.toString(arry);
	}
	
	//shell sort寻找到更好的实现并学习
	/*优点：
	 * 	1、多个子序列的排序交叉进行，写的可以更简洁
	 *  2、for循环里贴着一个if判断，可以把判断放到for循环内写
	*/
	public static String prac2_shell_sort_2(int[] arr) {
		int length = arr.length;
		for(int gap = length/2; gap > 0; gap/=2) {//控制gap值
			for(int i = gap; i < length ; i++) {
				//这个i的遍历没有错，其实质是多个子序列的排序交叉进行，而不是一个子序列一个子序列的进行排序，写法更简洁
				insertI(arr, gap, i);
			}
		}
		return Arrays.toString(arr);
	}
	
	/*
	 * for循环里贴着一个if判断，可以把判断放到for循环内写
	 * for循环的第三部分代表循环步长，因此shell sort的本质，就是不断改变直接插入算法中的步长，来改变插入内容
	*/
	public static void insertI(int[] arr, int gap, int i) {
		//这个方法是插入排序的其中一个人的插入
		//个人认为这是错位比较、并交换的场景下，最简洁的写法了
		//！！确实的减少了交换的次数！！
		int inserted = arr[i];
		int j;
		for(j = i - gap; j >= 0 && inserted < arr[j]; j -= gap) {
			//拿着最后一个值从后往前一个一个比，直到放到合适自己的位置，默认前面内容已排序好
			arr[j + gap] = arr[j];
		}
		arr[j + gap] = inserted;
	}
	

	//prac3：选择排序：直接选择，堆排序
	/*
	1、说明：
		每一次遍历，选择出当前遍历过程中的最小值，与当前遍历区间的最左侧交换，缩小遍历区间，重复
	2、细节优化：
		可否优化交换次数：222行，224行，228行，减少了交换次数
		可否优化比较次数：221行，减少了一轮(n)的无意义比较
	3、复杂度：
		时间复杂度:O(n^2)
		空间复杂度：O(1)
	4、其他：
		直接选择是不稳定的排序算法：
		如何理解不稳定：
			所有相等的数经过某种排序方法后，仍能保持它们在排序之前的相对次序，我们就说这种排序方法是稳定的
			根据一个关键字进行排序，有可能破坏另一个关键字的顺序，则不稳定
	*/

	public static String prac3_straight_select_sort(int[] arry) {
		for(int i = 0; i < arry.length - 1; i++){
			int place = i;//内层每次循环只需记住这层循环里最小值的位置就可以了，然后再拿着位置值和开头的值替换
			for(int j = i + 1; j < arry.length; j++){
				if(arry[place] > arry[j]) {
					place = j;
				}
			}
			if(place != i) {//提高效率，位置不变，则不用替换了
				int tmp = arry[place];
				arry[place] = arry[i];
				arry[i] = tmp;
			}
		}
		return Arrays.toString(arry);
	}



	//需再多了解：完美二叉树, 完全二叉树和完满二叉树。https://www.cnblogs.com/idorax/p/6441043.html
	//完美二叉树（perfect binary tree）：Every node except the leaf nodes have two children and every level (last level too) is completely filled. 除了叶子结点之外的每一个结点都有两个孩子，每一层(当然包含最后一层)都被完全填充
	//完全二叉树（complete binary tree）：Every level except the last level is completely filled and all the nodes are left justified. 除了最后一层之外的其他每一层都被完全填充，并且所有结点都保持向左对齐。
	//完满二叉树（full/strictly binary tree）：Every node except the leaf nodes have two children. 除了叶子结点之外的每一个结点都有两个孩子结点。
	//完美二叉树 一定是 完全二叉树或完满二叉树
	//完满二叉树   不一定是  完全二叉树
	//完全二叉树   不一定是  完满二叉树
	//即使完满二叉树又是完全二叉树   不一定是完美二叉树

	//堆排序
	/*
	1、说明：
		利用了完全二叉 树的特性
			当树结构为完全二叉树的时候，二叉树可以用数组的形式表示（n、2n+1、2n+2）
		一次建堆操作，多次下沉操作，完成排序
			建堆操作：和插入排序的逻辑很像，即向一个已建立好的堆内下沉一个元素，直至所有人都沉了一遍，建堆完毕
	2、细节优化：
		可否优化交换的次数：尚未发现
		可否优化比较的次数：建堆优化，从length/2 - 1 开始建堆
	3、复杂度：
		时间复杂度：O(nlog2n)，个人认为堆的时间复杂度计算和二分查找的时间复杂度计算很像
		空间复杂度：O(1)
	4、其他：
		最大堆：根最大，因此小的向下沉
		最小堆：根最小，因此大的向下沉
		描述的都是那个尖儿的大小
		堆的删除操作：
			删除堆顶元素，尾元素补上，并下沉
			删除任意节点元素，尾元素补上，并从补上的位置下沉
		堆的插入操作：
			放在最末尾，再上浮（用list可以实现一下）father = n/2
	*/
	public static String prac3_heap_sort(int[] arry) {
		
		for(int i = 0; i < arry.length; i++) {//arry[0]是根节点，每次做完建堆操作之后，最大数出现在大顶堆的顶部，因此将最大值放到最后即可
											  //此处处理很像冒泡排序的处理，不需要管最后一个值时的for循环写法
			maxheapify(arry, arry.length - i);
			int tmp = arry[0];
			arry[0] = arry[arry.length - 1 - i];//此处的arry.length - 1 - i  也和冒泡的处理方式一样。
			arry[arry.length - 1 - i] = tmp;
		}
		
		return Arrays.toString(arry);
	}
	
	/*
	 * 完成单个堆的建堆操作
	*/
	public static void maxheapify(int[] arry, int size) {
		
		if(size == arry.length) {//建堆
			for(int i = size/2 - 1; i >= 0; i--) {
				heapify(arry, i, size);
			}
		}else {//单次下沉
			heapify(arry, 0, size);
		}
		
	}
	
	/*
	 * 完成单个节点值的下沉操作
	 * currNode 是当前结点在堆中的位置
	 * size 是堆的大小
	*/
	public static void heapify(int[] arry, int currentNode, int size) {
		
		if(currentNode >= size) {
			return;
		}
		int left = currentNode * 2 + 1;
		int right = currentNode * 2 + 2;
		
		int max = currentNode;
		//先比左后比又，有可能左有右没有
		if(left < size && arry[left] > arry[max]) {//左下的值比父大，需要让大的值上去，小的值沉下去
			max = left;
		}
		if(right < size && arry[right] > arry[max]) {//右下比左或父都大，把最大值上去
			max = right;
		}
		if(max != currentNode) {//如果最大的节点不是当前的父节点，那么一定发生了下沉。1、完成相应值的替换。2、就下沉的一边继续向下递归下沉操作。
			int tmp = arry[max];
			arry[max] = arry[currentNode];
			arry[currentNode] = tmp;
			heapify(arry, max, size);//将下沉的那一边节点继续当作父节点，向下递归比较
		}
	}
	
	
	
	//prac4：交换排序：冒泡排序，快速排序
	
	//冒泡排序
	/*
	1、说明：
		左右元素依次比较，保证每次比较后，右侧的元素都更大，反复比较arry.len轮
	2、细节优化：
		可否减少交换次数：352行，减少了交换次数
		可否减少比较次数：349行，减少了比较次数
	3、复杂度：
		时间复杂度：O(n^2)
		空间复杂度：O(1)
	4、其他：
		冒泡排序是稳定的，而且很关键的是，352行的比较，好像是确保他稳定的原因
	*/
	/*
	 * 将序列当中的左右元素，依次比较，保证右边的元素始终大于左边的元素；
	 * （ 第一轮结束后，序列最后一个元素一定是当前序列的最大值；）
	 * 第二轮可以不考虑最后一个了 以此类推
	*/
	public static String prac3_bubble_sort(int[] arry) {
		for(int i = 0; i < arry.length; i++) {
			for(int j = 0; j < arry.length - 1 - i; j ++) {
				if(arry[j] > arry[j + 1]) {
					int tmp = arry[j];
					arry[j] = arry[j + 1];
					arry[j + 1] = tmp;
				}
			}
		}
		return Arrays.toString(arry);
	}
	
	//快速排序
	public static String prac3_quick_sort(int[] arry) {
		if(arry.length <= 1) {
			return Arrays.toString(arry);
		}
		quickSort(arry, 0, arry.length-1);
		return Arrays.toString(arry);
	}
	
	public static void quickSort(int[] arry, int left, int right) {
		int begin =  left;
		int end = right;
		if(left > right) {
			return;
		}
		while(left != right) {
			while(right > left && arry[right] >= arry[begin]) {
				right --;
			}
			while(left < right && arry[left] <= arry[begin]) {
				left ++;
			}
			int tmp = arry[right];
			arry[right] = arry[left];
			arry[left] = tmp;
		}
		int tmpBase = arry[begin];
		arry[begin] = arry[left];
		arry[left] = tmpBase;
		quickSort(arry, begin, left - 1);
		quickSort(arry, left + 1, end);
	}


	//尝试寻找快排的更优实现思路
	/*
	 * 优点：
	 * 	1、将数组最左值和数组最右值作为参数传进去了，这样能保证递归时始终传递同一个数组对象，不用在返回时考虑赋值了
	 *  2、在i、j指针遍历时，直接i指针从基点开始遍历，让遍历步骤写的更易读更简洁
	 *  3、判断条件设置使用&&，同样让遍历判断的代码更加简洁
	 *  4、使用多一个方法封装，实现本来外部就直接传进来一个数组就足够了，这件事。无需外部再传进来left、right值
	 *
	 * 关于为什么必须先移动基点对向的指针的问题:
	 * 	不管分什么情况讨论，在先动右指针的情况下，左指针应该都是停留在 <=基点的位置;
	 *  如果反过来左指针就会停留在大于等于基点的位置;
	 */
	public static void prac7_chongxie(int[] arry) {
		if(arry.length <= 1) {
			return;
		}
		quickSort2(arry, 0, arry.length - 1);
		return;
	}

	public static void quickSort2(int[] arry, int left, int right) {
		if(left > right) {
			return;
		}
		int base = arry[left];
		int i = left;
		int j = right;
		while(i != j) {
			while(i < j && arry[j] >= base) {
				j--;
			}
			while(i < j && arry[i] <= base) {
				i++;
			}
			int tmp = arry[i];
			arry[i] = arry[j];
			arry[j] = tmp;
		}
		arry[left] = arry[i];
		arry[i] = base;
		quickSort(arry, left, i - 1);
		quickSort(arry, i + 1, right);
		return;
	}
	
	
	// prac4:归并排序----------------------------------------------
	//https://blog.csdn.net/k_koris/article/details/80508543
	//把一个数组切割为两个，只需要三个点即可
	public static String prac4_merge_sort(int[] arry) {
		if(arry.length <= 1) {
			return Arrays.toString(arry);
		}
		merge_sort(arry, 0, arry.length - 1);
		return  Arrays.toString(arry);
	}
	
	/*
	 * 计算mid值时：
	 * 当使用(left + right)/2的方式计算中间值时，由于整数向下取整，在递归到最后的时候，mid只能无限次等于left，且无限次小于right，因此mid在作为left值时至少+1
	*/
	public static void merge_sort(int[] arry, int left, int right) {
		if(left >= right) {
			return;
		}
		int mid = (left + right)/2;
		merge_sort(arry, left, mid);
		merge_sort(arry, mid + 1, right);
		merge(arry, left, mid, right);
	}
	
	//mid这个值要分给left使用，这样在两个数组成的数组中可以做到两个数进行排序
	public static void merge(int[] arry, int left, int mid, int right) {
		if(left >= right) {
			return;
		}
		//left和right都是数组的下标值，因此长度值应+1
		int[] res = new int[right - left + 1];
		int l = left;
		int m = mid + 1;
		int r = right;
		for(int i = 0; i < res.length; i++) {
			/*
			 * 前两个if：先判断左右两个数组是否某一个已被输出完，输出完则输出另一个数组
			 * 若两数组未输出完，则比较值大小，输出其中更小的那个并移动更小的那个的指针
			*/
			if(l > mid) {
				res[i] = arry[m];
				m++;
			}else if(m > right) {
				res[i] = arry[l];
				l++;
			}else if(arry[l] < arry[m]) {
				res[i] = arry[l];
				l++;
			}else if(arry[l] >= arry[m]){
				res[i] = arry[m];
				m++;
			}
		}
		for(int i = 0; i < res.length; i++) {
			arry[left] = res[i];
			left++;
		}
	}
	
	// prac5：基数排序/桶排序
	// 限用于正整数的数组排序
	public static String prac5_basic_sort(int[] arry) {
		
		int times = findTimes(arry);
		basic_sort(arry, times);
		return Arrays.toString(arry);
	}
	
	//找到数组最大的一个数有几位，判断需要做多少次循环
	public static int findTimes(int[] arry) {
		int count = 0;
		int max = 0;
		for(int each : arry) {
			max = max < each?each:max;
		}
		while(max > 0) {
			max = max / 10;
			count ++;
		}
		return count;
	}
	
	public static void basic_sort(int[] arry, int times) {
		ArrayList<LinkedList<Integer>> basicArray = new ArrayList<LinkedList<Integer>>();
		for(int i = 0; i < 10; i++) {
			basicArray.add(new LinkedList<Integer>());
		}
		int index = 1;
		while(times > 0) {
			times--;
			for(int i = 0; i < arry.length; i++) {
				LinkedList<Integer> linkedList = basicArray.get(getIndexNum(arry[i], index));
				linkedList.add(arry[i]);
			}
			index *=10;
			transFormArray(basicArray, arry);
		}
	}
	
	//index: 1、10、100、1000……
	public static int getIndexNum(int num,int index) {
		return num/index%10;
	}
	
	public static void transFormArray(ArrayList<LinkedList<Integer>> from, int[] to) {
		int i = 0;
		for(LinkedList<Integer> linkedList: from) {
			for(Integer integer: linkedList) {
				to[i] = integer;
				i++;
			}
			linkedList.clear();
		}
	}
	
}



















