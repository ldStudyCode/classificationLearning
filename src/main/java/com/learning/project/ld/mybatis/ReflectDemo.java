package com.learning.project.ld.mybatis;

import java.lang.reflect.Field;

/**
 * 反射demo
 */
public class ReflectDemo {
    public static void main(String[] args){

    }
    public static <T> void reflectSetValue(Class<T> classz){
        //要反射的类的class
        Class reflectClass =  classz;
        //获取所有反射字段
        Field[] fields = reflectClass.getFields();
        for(Field field:fields){
            //设置权限为public
            field.setAccessible(true);
        }
    }
}
