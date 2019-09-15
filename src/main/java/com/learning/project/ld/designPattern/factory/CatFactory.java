package com.learning.project.ld.designPattern.factory;

/**
 * 工厂方法类
 * Calendar 类中有应用实现createCalendar方法
 * @author lvdong
        * date 2019-08-26
        */
public class CatFactory {
    public Cat getCat(String type){
        Cat cat=null;
        if("red".equals(type)){
            cat=new RedCat();
        }else {
            cat=new BlueCat();
        }
        return cat;
    }
}
interface Cat{

}
class RedCat implements Cat{

}
class BlueCat implements Cat{

}