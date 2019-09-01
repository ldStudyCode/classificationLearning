package com.learning.project.gfq.privateNote.designPattern.SingleModel;

public class SingleModel {
    /*
    1、掉任一个类的静态方法，不会走构造方法
        构造方法只有在形成对象的时候才会去构造，调用构造方法
        而调用静态方法没有new对象，只有装载类

    2、单例模式的几个必写
        0、单例这个实例必须至少是 private static
        1、构造函数私有化
        2、getInstance一定static

    3、各自的特殊点：
        1、doubleSync
            一把锁临界区synchronized
            一把锁进制代码重排volatile
        2、Hungry
            装载即创建，空间换时间
            需final维持单例
        3、Lazy
            一把锁直接锁住getInstance就完了synchronized
        4、StaticClass
            利用了内部类的装载异步，且装载只一次
            外部类内部类都需要私有化构造函数
            内部类要static，getInstance直接拿就行
    */


}
