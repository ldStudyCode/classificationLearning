package com.learning.project.gfq.privateNote.designPattern.SingleModel;

public class StaticClassModel {
    /*
    静态内部类
        0、单例这个实例必须是 private static
        1、私有化构造函数
        2、getInstance一定static
        2、在（静态内部类 + static）的方式 或 （静态内部类 + 成员变量直接初始化）的方式
            利用了类装载只有一次的方式实现了单例
        3、线程安全
        4、延时加载。效率高
    为什么内部类会晚于其他加载：
        实际上，无论是外部类还是静态内部类
        对JVM而言，他们是平等的两个InstanceClass对象
        只存在访问修饰符限制访问权限的问题
        不存在谁包含谁的问题
    本质：
        利用了类装载异步，且类装载只有一次的特点
        实现了既能使用时才装载，又能线程安全
    */

    private static class ClassGetInstance {
        private static StaticClassModel staticClassModel = null;
        static {
            staticClassModel = new StaticClassModel();
        }
        private ClassGetInstance() {

        }
    }
    private StaticClassModel(){
    }
    public static StaticClassModel getInstance() {
        return ClassGetInstance.staticClassModel;
    }
}
