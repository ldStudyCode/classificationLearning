package com.learning.project.gfq.privateNote.algorithmBookNote.capture2;

import javax.print.DocFlavor;

public class Selection {
    /*
    选择排序：
        选择排序，它在不断地选择“剩余元素之中”的最小者

    *** 超重点
    * 无论数组如何，都是 N^2/2 次比较和 N 次交换
    * 属于原地排序算法

    对于长度为 N 的数组，选择排序需要大约 N^2/2 次比较和 N 次交换

    特点1：
        运行时间和输入无关
        为了找出最小的元素而扫描一遍数组并不能为下一遍扫描提供什么信息
        这种性质在某些情况下是缺点，因为使用选择排序的人可能会惊讶地发现，
        一个已经有序的数组或是主键全部相等的数组和一个元素随机排列的数组所用的排序时间竟然一样长！

    特点2：
        数据移动是最少的。
        每次交换都会改变两个数组元素的值，因此选择排序用了 N 次交换——交 换次数和数组的大小是线性关系。
        我们将研究的其他任何算法都不具备这个特征（大部分的增长数 量级都是线性对数或是平方级别
    */

    public static void sort(Comparable[] a) {
        // 将a[]按升序排列
        int N = a.length;
        // 数组长度
        for (int i = 0; i < N; i++) {
            // 将a[i]和a[i+1..N]中最小的元素交换
            int min = i;
            // 最小元素的索引
            for (int j = i+1; j < N; j++)
                if (Example.less(a[j], a[min]))
                    min = j;
            Example.exch(a, i, min);
        }
    }
    public static void main(String[] args){
        // 从标准输入读取字符串，将它们排序并输出
        String[] a = {"AWDAWD","ADFWD","AGEFDW","AFAWDC"};
        sort(a);
        assert Example.isSorted(a):"未排序成功！";
        Example.show(a);
    }

}
