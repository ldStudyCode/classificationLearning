package com.learning.project.gfq.privateNote.algorithmBookNote.capture1;

import java.util.Arrays;

public class Basic {

    public static void num_1_1_1() {
        /*
        给出以下表达式的值
        a.(0 + 15) / 2
        b.2.0e-6 * 100000000.1
        c.true && false || true && true
        */
        System.out.println((0 + 15) / 2);//int 向下取整
        System.out.println(2.0e-6 * 100000000.1);//2C乘10的负六次方
        System.out.println(true && false || true && true);//&& 优先级大于 ||
    }

    /*
    基础题：1.1.1 - 1.1.25
    */

    public static void num_1_1_2() {
        /*
        给出以下表达式的类型和值
        a. (1 + 2.236)/2
        b. 1 + 2 + 3 + 4.0
        c. 4.1 >= 4
        d. 1 + 2 + "3"
        */
        System.out.println((1 + 2.236)/2);//int与float相加，自动向更精确的转换
        System.out.println(1 + 2 + 3 + 4.0);//同上
        System.out.println(4.1 >= 4);//int 与 float做比较，自动向更精确的转换
        System.out.println(1 + 2 + "3");//加法同级，因此从左至右执行。先执行1+2=3，再执行遇到字符串会自动将+号另一侧自动变为字符
    }

    public static void num_1_1_3(int a, int b, int c){
        /*
        编写一个程序，从命令行得到三个整数参数。如果它们都相等则打印 equal，否则打印 not equal
        */
        if(a == b && b == c) {
            System.out.println("equal");
        }else {
            System.out.println("not equal");
        }
    }

    public static void num_1_1_4() {
        /*
        下列语句各有什么问题（如果有的话）？
        a. if (a > b) then c = 0;
        b. if a > b { c = 0; }
        c. if (a > b) c = 0;
        d. if (a > b) c = 0 else b = 0;
        */
        System.out.println("a.java中if没有与then搭配 ");
        System.out.println("b.if表达式需添加括号将布尔表达式括起来");
        System.out.println("c.正确");
        System.out.println("d.c = 0后面需加分号");
    }

    public static void num_1_1_5(double x, double y) {
        /*
        编写一段程序
        如果 double 类型的变量 x 和 y 都严格位于 0 和 1 之间则打印 true
        否则打印 false
        */
        if(0 <= x && x <= 1 && 0 <= y && y <= 1) {
            System.out.println("true");
        }else {
            System.out.println("false");
        }
    }

    public static void num_1_1_6() {
        /*
        下面这段程序会打印出什么？
        int f = 0;
        int g = 1;
        for (int i = 0; i <= 15; i++) {
            StdOut.println(f);
            f = f + g;
            g = f - g;
        }
        */
        System.out.println("斐波那契数列");
        int f = 0;
        int g = 1;
        for (int i = 0; i <= 15; i++) {
            System.out.println(f);
            f = f + g;
            g = f - g;
        }
        /*
        斐波那契数列是这样的一个数列，0，1，1，2，3，5，8，..，
        前两项都是1，后面每一项都是其前面两项的和
        g为第一项，f为第二项
        先移动f，f = f + g；
        再移动g，g = f - g；
        */
    }

    public static void num_1_1_7() {
        /*
        分别给出以下代码段打印出的值：
        */
        //a.
        System.out.println("a.求9的算术平方根");
        double t = 9.0;
        while (Math.abs(t - 9.0/t) > .001)//.001  === 0.001   n 与 9/n 应当想到等，则完全是算术平方根，若差值在容忍范围之外，则采取计算措施将差距缩小
            t = (9.0/t + t) / 2.0;//若差值大，则差值一定在n 与 9/n 之间
        System.out.printf("%.5f\n", t);

        //b.
        System.out.println("0-999的和");
        int sumb = 0;
        for (int i = 1; i < 1000; i++)
            for (int j = 0; j < i; j++)
                sumb++;
        System.out.println(sumb);

        //c.
        System.out.println("打印10 * 1000的值");
        int sumc = 0;
        for (int i = 1; i < 1000; i *= 2)// 2^0 到 2^9 共10次
            for (int j = 0; j < 1000; j++)//0 到 999 共1000次
                sumc++;//共10 * 1000 = 10000次
        System.out.println(sumc);
    }

    public static void num_1_1_8() {
        /*
        下列语句会打印出什么结果？给出解释。
        a. System.out.println('b');
        b. System.out.println('b' + 'c');
        c. System.out.println((char) ('a' + 4));
        */
        System.out.println("a.打印出b，char会自动转换toString打印");
        System.out.println("b.打印出197，两个ASCII码的和，char类型的加法重写为ASCII相加");
        System.out.println("c.打印出e，ASCII + 4 再转换为char类型打印");
    }

    public static void num_1_1_9(int n) {
        /*
        编写一段代码，将一个正整数 N 用二进制表示并转换为一个 String 类型的值 s
        */
        StringBuffer str1 = new StringBuffer();
        for(int i = n ; i > 0; i /= 2) {
            str1.append(i%2);
        }
        System.out.println(str1.reverse());
        String str2 = new String();
        for (int i = n; i > 0; i /= 2) {
            str2 = i%2 + str2;//字符串也可以反着拼啊！
        }
        System.out.println(str2);
    }

    public static void num_1_1_10() {
        /*
        下面这段代码有什么问题？
        int[] a;
        for (int i = 0; i < 10; i++)
            a[i] = i * i;
        */
        System.out.println("数组并没有声明内存占用空间，使用会报编译错误，a未被初始化");
    }

    public static void num_1_1_11(boolean [][] x) {
        /*
        编写一段代码，打印出一个二维布尔数组的内容。其中，使用 * 表示真，空格表示假。打印出行号和列号
        */
        System.out.print(" \t");
        for (int j = 0; j < x[0].length; j++) {
            System.out.print(j + "\t");
        }
        System.out.println("");
        for (int i = 0; i < x.length; i++) {
            System.out.print(i + "\t");
            for (int j = 0; j < x[i].length; j++) {
                if (x[i][j] == true) {
                    System.out.print("*\t");
                }else {
                    System.out.print(" \t");
                }
            }
            System.out.println("");
        }
    }

    public static void num_1_1_12() {
        /*以下代码段会打印出什么结果？
        int[] a = new int[10];
        for (int i = 0; i < 10; i++)//9 - 0
            a[i] = 9 - i;
        for (int i = 0; i < 10; i++)//0 - 9
            a[i] = a[a[i]];
        for (int i = 0; i < 10; i++)
            System.out.println(i);//打印0-9
        */
        System.out.println("最终打印0-9");
    }

    public static void num_1_1_13(int [][] x) {
        /*
        编写一段代码，打印出一个 M 行 N 列的二维数组的转置（交换行和列）
        */
        int m = x.length;
        int n = x[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(x[j][i] + "\t");
            }
            System.out.println();
        }
    }

    public static void num_1_1_14(int n) {
        /*
        编写一个静态方法 lg()，接受一个整型参数 N，返回不大于 log2N 的最大整数。不要使用 Math 库
        */
        /*
        log函数是次方函数的逆运算
        2^5 = 32
        那么log函数则是求，2的多少次方为32
        */
        int i = 1;
        while (i < n)
            i *= 2;
        System.out.println(i/2);
    }

    public static void num_1_1_15(int[] a, int m) {
        /*
        编写一个静态方法 histogram()
        接受一个整型数组 a[] 和一个整数 M 为参数并返回一个大小为M的数组，
        其中第i个元素的值为整数i在参数数组中出现的次数。
        如果a[]中的值均在0到M-1 之间，
        返回数组中所有元素之和应该和 a.length 相等。
        */
        int[] res = new int[m];
        for (int i = 0; i < a.length; i++) {
            if(0 <= a[i] && a[i] <= m-1) {
                int tmp = res[a[i]];
                res[a[i]] = ++tmp;
            }
        }
        System.out.println(Arrays.toString(res));
    }

    public static void num_1_1_16() {
        /*
        给出 exR1(6) 的返回值：
        public static String exR1(int n) {
            if (n <= 0)
                return "";
            return exR1(n-3) + n + exR1(n-2) + n;
        }
        */
        System.out.println("311361142246");
    }

    public static void num_1_1_17() {
        /*
        找出以下递归函数的问题：
        public static String exR2(int n) {
            String s = exR2(n-3) + n + exR2(n-2) + n;
            if (n <= 0)
                return "";
            return s;
        }
        */
        System.out.println("这个递归函数每次进入都会先进行递归，无任何条件使其return，最终会造成次数过多栈内存溢出。StackOverflowError");
    }

    public static void num_1_1_18() {
        /*
        请看以下递归函数：
        public static int mystery(int a, int b) {
            //两数相乘的算法！
            //两个数相乘可以换成其中一个数的多个幂函数相加
            if (b == 0)
                return 0;
            if (b % 2 == 0)
                return mystery(a+a, b/2);
            return mystery(a+a, b/2) + a;
        }
        public static int mystery(int a, int b) {
            if (b == 0)
                return 1;
            if (b % 2 == 0)
                return mystery(a*a, b/2);
            return mystery(a*a, b/2) * a;
        }
        a.mystery(2, 25) 和 mystery(3, 11) 的返回值是多少？
        b.给定正整数 a 和 b，mystery(a,b) 计算的结果是什么？
        c.将代码中的 + 替换为 * 并将 return 0 改为 return 1，然后回答相同的问题
        */
        System.out.println("a.mystery(2, 25)=50, mystery(3, 11)=33");
        System.out.println("b.mystery(a,b)计算结果是两数相乘");
        System.out.println("c-a.mystery(2,25)=33554432, mystery(3, 11)=177147");
        System.out.println("c-b.mystery(a, b)计算结果是a的b次方的结果");
    }

    public static double[] num_1_1_19(int n) {
        /*
        在计算机上运行以下程序：
        public class Fibonacci {
            public static long F(int N) {
                if (N == 0)
                    return 0;
                if (N == 1)
                    return 1;
                return F(N-1) + F(N-2);
            }
            public static void main(String[] args) {
                for (int N = 0; N < 100; N++)
                    StdOut.println(N + " " + F(N));
            }
        }
        计算机用这段程序在一个小时之内能够得到 F(N) 结果的最大 N 值是多少？
        答：
            最大值是2.189229958345552E20
        开发 F(N) 的一 个更好的实现，用数组保存已经计算过的值。
        答：
            原实现不好的原因是总是计算过去已计算过的值，重复计算太多，可以用动态规划的思想实现
        */
        double[] res = new double[n];
        res[0] = 0;
        res[1] = 1;
        for (int i = 2; i < n; i++) {
            res[i] = res[i-1] + res[i-2];
        }
        return res;
    }

    public static double num_1_1_20(int n) {
        /*
        编写一个递归的静态方法计算 ln(N!) 的值
        "log函数内部相乘，可以拆成外部相加"
        */
        if(n == 1)
            return 0.0;
        return Math.log(n) + num_1_1_20(n-1);
    }

    public class _1_1_21{
        private String name;
        private double num1;
        private double num2;
        public String getName() {
            return name;
        }
        public double getNum1() {
            return num1;
        }

        public double getNum2() {
            return num2;
        }
        public void setName(String name) {
            this.name = name;
        }
        public void setNum1(double num1) {
            this.num1 = num1;
        }
        public void setNum2(double num2) {
            this.num2 = num2;
        }
    }
    public static void num_1_1_21(_1_1_21[] chart) {
        /*
        编写一段程序
        从标准输入按行读取数据，其中每行都包含一个名字和两个整数
        然后用 printf() 打印一张表格
        每行的若干列数据包括名字、两个整数和第一个整数除以第二个整数的结果，精确到小数点后三位
        可以用这种程序将棒球球手的击球命中率或者学生的考试分数制成表格
        */
        for (int i = 0; i < chart.length; i++) {
            System.out.print(chart[i].getName() + " \t"
                    + chart[i].getNum1() + " \t");
            System.out.printf("%.3f\t", (float)chart[i].getNum1()/chart[i].getNum2());
            System.out.println();
        }
    }

    public static void num_1_1_22() {
        /*
        使用 1.1.6.4 节中的 rank() 递归方法重新实现 BinarySearch 并跟踪该方法的调用。
        每当该方法被调用时，打印出它的参数 lo 和 hi 并按照递归的深度缩进。
        提示：为递归方法添加一个参数 来保存递归的深度
        */
        int[] a = {1,2,4,7,8,9,12,13,15,17,18,23,26,31};
        rank(7, a);
    }
    public static int rank(int key, int[] a) {
        return rank(key, a, 0, a.length - 1);
    }
    public static int rank(int key, int[] a, int lo, int hi) {
        //如果key存在于a[]中，它的索引不会小于lo且不会大于hi
        if (lo > hi)
            return -1;
        int mid = lo + (hi - lo) / 2;

//        for (int i = 0; i < a.length; i++) {
//            if (lo == i) {
//                System.out.print(lo + "\t");
//            }else if(hi == i) {
//                System.out.print(hi + "\t");
//            }else if(mid == i) {
//                System.out.print(mid + "\t");
//            }else {
//                System.out.print("-\t");
//            }
//        }
//        System.out.println();

        if (key < a[mid])
            return rank(key, a, lo, mid - 1);
        else if (key > a[mid])
            return rank(key, a, mid + 1, hi);
        else
            return mid;
    }

    public static void num_1_1_23() {
        /*
        为 BinarySearch 的测试用例添加一个参数：
        + 打印出标准输入中不在白名单上的值；
        -，则打印出标准输入中在白名单上的值
        */
        int[] a = {1,2,412,23,1,2,3,24,12,1,2,31,23,62,346,12,4,7,8,9,12,13,15,17,18,23,26,31};//白名单
        Arrays.sort(a);
        int[] keySet = {1,2,42,532,123,24,12,31,5324,213,32,12,64};
        String param = "-";
        for (int i = 0; i < keySet.length; i++) {
            if (null != param && !"".equals(param)) {
                if("+".equals(param) && rank(keySet[i], a) < 0) {//不在白名单中的
                    System.out.print(keySet[i] + "\t");
                }else if("-".equals(param) && rank(keySet[i], a) > 0) {//在白名单中的
                    System.out.print(keySet[i] + "\t");
                }
            }
        }
    }
    public class BinarySearch {
        public int rank(int key, int[] a) {
            // 数组必须是有序的
            int lo = 0;
            int hi = a.length - 1;
            while (lo <= hi) {
                // 被查找的键要么不存在，要么必然存在于 a[lo..hi] 之中
                int mid = lo + (hi - lo) / 2;
                if (key < a[mid])
                    hi = mid - 1;
                else if (key > a[mid])
                    lo = mid + 1;
                else return mid;
            }return -1;
        }
    }

    public static void num_1_1_24() {
        /*
        给出使用欧几里德算法计算 105 和 24 的最大公约数的过程中得到的一系列 p 和 q 的值。
        扩展该 算法中的代码得到一个程序 Euclid，
        从命令行接受两个参数，计算它们的最大公约数并打印出每次调用递归方法时的两个参数。
        使用你的程序计算 1 111 111 和 1 234 567 的最大公约数。
        */
        /*
        欧几里得算法
        计算两个非负整数 p 和 q 的最大公约数：
        若 q 是 0，则最大公约数为 p
        否则，将 p 除以 q 得到余数 r，p 和 q 的最大公约数即为 q 和 r 的最大公约数
        public static int gcd(int p, int q) {
            if (q == 0)
                return p;
            int r = p % q;
            return gcd(q, r);
        }
        */
        System.out.println("最大公约数为：" + gcd(1111111, 1234567));
    }

    public static int gcd(int p, int q) {
        System.out.println(p + "\t"+ q);
        if (q == 0)
            return p;
        int r = p % q;//小数子%大数字，结果是小数字本身，因此可以自动交换位置
        return gcd(q, r);//除数当作大数字，余数当作小数子
    }

    public static void num_1_1_25() {
        /*
        使用数学归纳法证明欧几里德算法能够计算任意一对非负整数 p 和 q 的最大公约数
        公约数：
            亦称“公因数”。它是一个能被若干个整数同时均整除的整数
        */
        String str = "" +
                "假设有正整数p和q，且p>q，则一定有 kq+r=p\n" +
                "现在假设a是p和q的一个公约数，给等式两边都除以a，有k(q/a)+r/a=p/a\n" +
                "得b+r/a=d，可知b和d都是整数，则r/a一定也是整数。\n" +
                "也就是说，a是p、q、r三者的公约数，而r又是p除以q的余数。\n" +
                "所以有，两个整数的公约数等于其中较小的那个数和两数相除余数的公约数。\n" +
                "公约数都相等，那么最大公约数自然也相等。\n" +
                "即有gcd(p,q)=gcd(q,p%q)（p>q）\n" +
                "定理成立。证毕\n";
        System.out.println(str);
    }

    public static void main(String args[]) {
//        num_1_1_1();
//        num_1_1_2();
//        num_1_1_3(1,1,2);
//        num_1_1_4();
//        num_1_1_5(1,0.822);//参数定义double类型，可以自动将int型进行转换
//        num_1_1_6();
//        num_1_1_7();
//        num_1_1_8();
//        num_1_1_9(10);
//        num_1_1_10();
//        boolean [][] x = {{true,false,true,true},{true,false,false,true},{false,false,false,true},{false,true,false,true}};
//        num_1_1_11(x);
//        num_1_1_12();
//        int[][] x = {{1,2,3,4},{2,3,4,5},{3,4,5,6},{5,6,7,8}};
//        num_1_1_13(x);
//        num_1_1_14(1223);
//        int[] a = {0,2,3,2,2,1,3,1,2,4,1,2,3,2};
//        num_1_1_15(a,5);
//        num_1_1_16();
//        num_1_1_17();
//        num_1_1_18();
//        System.out.println(Arrays.toString(num_1_1_19(100)));
//        System.out.println(num_1_1_20(7));
//        _1_1_21[] chart = new _1_1_21[3];//定义对象类型的数组，初始化数组长度，并不会初始化对象
//        Basic captureOnePrac = new Basic();
//        for (int i = 0; i < chart.length; i++) {
//            _1_1_21 tmp = captureOnePrac.new _1_1_21();
//            tmp.setName("姓名" + i);
//            tmp.setNum1((i+3)*(i+3));
//            tmp.setNum2((i+1)*(i+1));
//            chart[i] = tmp;
//        }
//        num_1_1_21(chart);
//        num_1_1_22();
//        num_1_1_23();
//        num_1_1_24();
        num_1_1_25();

    }
}

