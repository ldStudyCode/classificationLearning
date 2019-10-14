package com.learning.project.ld.designPattern.proxy;
/**
 * 代理模式
 * 继承同一接口
 * 优点：
 *  1.对目标对象有保护作用
 *  2.代理对象可扩展目标对象功能
 *  3.一定程度的解耦
 * 缺点：
 *  1.增加系统复杂度
 *  2.由于增加代理对象，处理速度有所下降
 */

interface Subject{void request();}
class RealSubject implements Subject{

    @Override
    public void request() {
        System.out.println("具体实现方法");
    }
}
class ProxySubject implements Subject{
    private RealSubject realSubject;
    @Override
    public void request() {
        if (realSubject==null) realSubject=new RealSubject();
        preRequest();
        realSubject.request();
        afterRequest();
    }
    public void preRequest() {
        System.out.println("访问具体实现方法之前的预处理。");
    }
    public void afterRequest()
    {
        System.out.println("访问具体方法之后的后续处理。");
    }
}
