package com.learning.project.gfq.privateNote.designPattern.SingleModel;

import org.apache.ibatis.cache.decorators.SynchronizedCache;

public class LazyModel {
    /*
    懒汉模式
        0、单例这个实例必须是 private static
        1、构造函数私有化
        2、getInstance一定static
        3、用的时候再装载（延时加载）
        4、线程安全需加锁,导致效率低
    本质：
        时间换空间
        需要注意线程安全问题
    */
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private static LazyModel lazyModel = null;
    private LazyModel() {

    }

    public static synchronized LazyModel getInstance() {
        if(null == lazyModel) {
            lazyModel = new LazyModel();
            return lazyModel;
        }else {
            return lazyModel;
        }
    }



}
