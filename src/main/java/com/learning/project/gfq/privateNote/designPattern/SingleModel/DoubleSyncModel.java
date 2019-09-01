package com.learning.project.gfq.privateNote.designPattern.SingleModel;

public class DoubleSyncModel {
    /*
    双重锁
    0、单例这个实例必须是 private static
    1、构造函数私有化
    2、getInstance一定static
    4、既延时加载、又能保证不因为锁而降低访问效率
    5、为了解决指令集重排，使用volatile，可以使用
        1）禁止代码重排序
        2）保证一个共享变量在被修改时保持唯一【每个线程有一块独占内存，独占内存中有变量副本】
    */

    private static volatile DoubleSyncModel doubleSyncModel = null;
    private DoubleSyncModel() {

    };

    public static DoubleSyncModel getInstance() {
        if(null == doubleSyncModel) {
            synchronized (doubleSyncModel) {
                if(null == doubleSyncModel) {
                    doubleSyncModel = new DoubleSyncModel();
                }
            }
        }
        return doubleSyncModel;
    }

}
