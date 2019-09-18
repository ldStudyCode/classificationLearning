package com.learning.project.ld.designPattern.proxy;
/**
 * 代理模式
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
