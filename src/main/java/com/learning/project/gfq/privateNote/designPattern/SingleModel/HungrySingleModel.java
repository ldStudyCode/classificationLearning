package com.learning.project.gfq.privateNote.designPattern.SingleModel;

public class HungrySingleModel {
    /*
    饿汉模式
        0、单例这个实例必须是 private static
        1、构造函数私有化
        2、getInstance一定static
        3、类装载的时候直接new一个
            1）static代码块可装载时执行一次
            2）静态成员变量可装载时初始化
        4、线程安全
        5、易占耗资源
    本质：
        空间换时间
    */
    private static final HungrySingleModel hungrySingleModel = new HungrySingleModel();
    /*
    or
    private static final HungrySingleModel hungrySingleModel;
    static {
        hungrySingleModel = new HungrySingleModel();
    }
    */
    private HungrySingleModel() {

    }
    public static HungrySingleModel getInstance() {
        return hungrySingleModel;
    }

}
