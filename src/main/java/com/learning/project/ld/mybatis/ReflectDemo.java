package com.learning.project.ld.mybatis;

import com.learning.project.ld.mybatis.entity.TestUserEntity;

import java.lang.reflect.Field;

/**
 * 反射demo
 */
public class ReflectDemo {
    public static void main(String[] args){
        TestUserEntity testDemo = null;
        System.out.println(testDemo==null);
        testDemo = reflectSetValue(TestUserEntity.class);
        System.out.println(testDemo==null);
    }
    public static <T> T reflectSetValue(Class<T> classz){
        //要反射的类的class
        T obj = null;
        //获取所有反射字段
        Field[] fields = classz.getDeclaredFields();
        try {
            obj = classz.newInstance();
            for(Field field:fields){
                String fieldName = field.getName();
                //设置权限为public
                field.setAccessible(true);
                //类型匹配
                if(field.getType().isInstance(new Integer(0))){
                    field.set(obj,1);
                }else{
                    field.set(obj,"1");
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
