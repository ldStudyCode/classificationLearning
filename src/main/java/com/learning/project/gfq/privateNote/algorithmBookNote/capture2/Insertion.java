package com.learning.project.gfq.privateNote.algorithmBookNote.capture2;

public class Insertion {
    /*
    插入排序
        将每一张牌插入到“其他已经有序的”牌中的适当位置
        在索引 i 由左向右变化的过程中，它左侧的元素总是有序的，所以当 i 到达数组的右端时排序就完成了

    *** 超重点
    * 部分有序的数组，插入排序十分高效
    * 属于原地排序算法

    *重点1：
    对于随机排列的长度为 N 且【主键不重复】的数组
    平均情况下插入排序需要～ N^2/4 次比 较以及～ N^2/4 次交换
    最坏情况下需要～ N^2/2 次比较和～ N^2/2 次交换
    最好情况下需要 N-1 次比较和 0 次交换

    特点1，时间：
        插入排序所需的时间取决于输入中元素的初始顺序。
        例如，
        对一个很大 且其中的元素已经有序（或接近有序）的数组进行排序
        将会比对随机顺序的数组或是逆序数组进行排序要快得多
        对于这种有序数组，插入排序的时间时线性的（N-1左右）而选择排序则是平方级的
    特点2：
        选择排序一样，当前索引左边的所有元素都是有序的
        但它们的最终位置还不确定，为了给 更小的元素腾出空间，它们可能会被移动
    空间:
        除了函数调用所需的栈和固定数目的实例变量之外无需额外内存的原地排序算法

    插入排序适用情况：
        适用于【部分有序数组】：
            如果数组中倒置的数量小于数组大小的某个倍数，那么我们说这个数组是部分有序的
            1、数组中每个元素距离它的最终位置都不远
            2、一个有序的大数组接一个小数组
            3、数组中只有几个元素的位置不正确
        PS.关键字【倒置】
            指的是数组中的两个顺序颠倒的元素
             E X A M P L E 中有 11 对倒置：E-A、 X-A、 X-M、 X-P、 X-L、 X-E、 M-L、 M-E、 P-L、 P-E 以及 L-E
    *重点2：
        插入排序需要的交换操作和数组中倒置的数量相同
            每次交换都改变了两个顺序颠倒的元素的位置，相当于减少了一对倒置，当倒置数量为 0 时，排序就完成了
        需要的比较次数大于等于倒置的 数量，小于等于倒置的数量加上数组的大小再减一
            每次交换都对应着一次比较，且 1 到 N-1 之间的每个 i 都可能需要一次 额外的比较

    *重点3：
        要大幅提高插入排序的速度并不难，只需要在内循环中将较大的元素都向右移动而不总是交换两个元素
        （这样访问数组的次数就能减半）
        见练习 2.1.25

    */
    public static void sort(Comparable[] a) {
        // 将a[]按升序排列
        int N = a.length;
        for (int i = 1; i < N; i++) {
            // 将 a[i] 插入到 a[i-1]、a[i-2]、a[i-3]...之中
            for (int j = i; j > 0 && Example.less(a[j], a[j-1]); j--)
                Example.exch(a, j, j-1);
        }
    }

    public static void main(String[] args){
        // 从标准输入读取字符串，将它们排序并输出
        String[] a = {"AWDAWD","ADFWD","AGEFDW","AFAWDC"};
        sort(a);
        //断言检查：assert <boolean表达式> : <错误信息表达式> 或 assert <boolean表达式>
        //如果<boolean表达式>为true，则程序继续执行
        //如果为false，则程序抛出java.lang.AssertionError，并输入<错误信息表达式>
        //IDE中，默认不启动，若启动，Run - Edit configurations - Configuration - VM options - "-ea"
        assert Example.isSorted(a):"未排序成功！";
        Example.show(a);
    }
}