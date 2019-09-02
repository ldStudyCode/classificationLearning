package com.learning.project.gfq.privateNote.algorithmBookNote.capture1;

public class Improve {

    public static void num_1_1_26() {
        /*
        将三个数字排序。
        假设 a、b、c 和 t 都是同一种原始数字类型的变量。
        证明以下代码能够将 a、 b、c 按照升序排列：
        if (a > b) {
            t = a; a = b; b = t;
        }
        if (a > c) {
            t = a; a = c; c = t;
        }
        if (b > c) {
            t = b; b = c; c = t;
        }
        */
        System.out.println("前两个if确保三个数字中最小值在A，最后一个if对后两个数进行排序");
    }

    public static void num_1_1_27() {
        /*
        二项分布。
        估计用以下代码计算 binomial(100, 50) 将会产生的递归调用次数：
        public static double binomial(int N, int k, double p) {
            if (N == 0 && k == 0)
                return 1.0;
            and if (N < 0 || k < 0)
                return 0.0;
            return (1.0 - p)*binomial(N-1, k, p) + p*binomial(N-1, k-1);
        }
        将已经计算过的值保存在数组中并给出一个更好的实现
        */
    }

    public static void main(String args[]) {

        num_1_1_26();


    }

}
