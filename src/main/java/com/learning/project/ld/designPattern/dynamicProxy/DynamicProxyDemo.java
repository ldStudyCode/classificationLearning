package com.learning.project.ld.designPattern.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理
 *
 */
public class DynamicProxyDemo implements InvocationHandler {
    private Object target;
    public DynamicProxyDemo(Object target){
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
