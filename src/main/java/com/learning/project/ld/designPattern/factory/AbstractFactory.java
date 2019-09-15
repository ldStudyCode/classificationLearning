package com.learning.project.ld.designPattern.factory;

import org.thymeleaf.util.StringUtils;

/**
 * 抽象工厂类
 * Connection实现类  SqlSessionFactory
 *
 * @author lvdong
 */

/**
 * 为形状创建一个接口

interface Shape{
    Shape draw();
}
class Rectangle implements Shape{
    @Override
    public Rectangle draw() {

        System.out.println("this is Rectangle draw method");
        return new Rectangle();
    }
}
class Square implements Shape {
    @Override
    public Square draw() {
        System.out.println("this is Square draw method");
        return new Square();
    }
}
class Circle implements Shape {
    @Override
    public Circle draw() {
        System.out.println("this is Circle draw method");
        return new Circle();
    }
}
interface CreateShapeFactory{
    public Shape createShape();
}
class CircleFactory implements CreateShapeFactory{

    @Override
    public Shape createShape() {
        return new Circle();
    }
}
class SquareFactory implements CreateShapeFactory{

    @Override
    public Shape createShape() {
        return new Square();
    }
}*/
interface IPhoneProduct{
    /**
     * 开机
     */
    void start();

    /**
     * 关机
     */
    void shutdown();

    /**
     * 拨打电话
     */
    void callUp();

    /**
     * 发送短信
     */
    void sendSMS();
}
/**
 * 路由器产品接口
 */
interface IRouterProduct {

    /**
     * 开机
     */
    void start();

    /**
     * 关机
     */
    void shutdown();

    /**
     * 开启wifi
     */
    void openWifi();

    /**
     * 设置参数
     */
    void setting();

}
/**
 * 小米手机产品
 */
class XiaomiPhone implements IPhoneProduct {
    @Override
    public void start() {
        System.out.println("开启小米手机");
    }

    @Override
    public void shutdown() {
        System.out.println("关闭小米手机");
    }

    @Override
    public void callUp() {
        System.out.println("用小米手机打电话");
    }

    @Override
    public void sendSMS() {
        System.out.println("用小米手机发短信");
    }
}
/**
 * 小米路由器产品
 */
class XiaomiRouter implements IRouterProduct {
    @Override
    public void start() {
        System.out.println("启动小米路由器");
    }

    @Override
    public void shutdown() {
        System.out.println("关闭小米路由器");
    }

    @Override
    public void openWifi() {
        System.out.println("打开小米路由器的wifi功能");
    }

    @Override
    public void setting() {
        System.out.println("设置小米路由器参数");
    }
}


/**
 * 华为手机产品
 */
class HuaweiPhone implements IPhoneProduct {
    @Override
    public void start() {
        System.out.println("开启华为手机");
    }

    @Override
    public void shutdown() {
        System.out.println("关闭华为手机");
    }

    @Override
    public void callUp() {
        System.out.println("用华为手机打电话");
    }

    @Override
    public void sendSMS() {
        System.out.println("用华为手机发短信");
    }
}

/**
 * 华为路由器产品
 */
class HuaweiRouter implements IRouterProduct {
    @Override
    public void start() {
        System.out.println("启动华为路由器");
    }

    @Override
    public void shutdown() {
        System.out.println("关闭华为路由器");
    }

    @Override
    public void openWifi() {
        System.out.println("打开华为路由器的wifi功能");
    }

    @Override
    public void setting() {
        System.out.println("设置华为路由器参数");
    }
}
/**
 * 抽象产品工厂
 */
interface IProductFactory {

    /**
     * 生产手机
     * @return
     */
    IPhoneProduct produceTelPhone();

    /**
     * 生产路由器
     * @return
     */
    IRouterProduct produceRouter();

}
/**
 * 小米产品工厂
 */
class XiaomiProductFactory implements IProductFactory {
    @Override
    public IPhoneProduct produceTelPhone() {
        System.out.println("生产小米手机");
        return new XiaomiPhone();
    }

    @Override
    public IRouterProduct produceRouter() {
        System.out.println("生产小米路由器");
        return new XiaomiRouter();
    }
}
/**
 * 华为产品工厂
 */
class HuaweiProductFactory implements IProductFactory{
    @Override
    public IPhoneProduct produceTelPhone() {
        System.out.println("生产华为手机");
        return new HuaweiPhone();
    }

    @Override
    public IRouterProduct produceRouter() {
        System.out.println("生产华为路由器");
        return new HuaweiRouter();
    }
}


/**
 * 客户端
 */
class Client {

    public static void main(String[] args) {
        System.out.println("小米系列产品");
        //小米产品工厂实例
        IProductFactory xiaomiProductFactory = new XiaomiProductFactory();
        //生产小米路由器
        IRouterProduct xiaomiRouter = xiaomiProductFactory.produceRouter();
        xiaomiRouter.start();
        xiaomiRouter.setting();
        xiaomiRouter.openWifi();
        xiaomiRouter.shutdown();
        //生产小米手机
        IPhoneProduct xiaomiPhone = xiaomiProductFactory.produceTelPhone();
        xiaomiPhone.start();
        xiaomiPhone.callUp();
        xiaomiPhone.sendSMS();
        xiaomiPhone.shutdown();

        System.out.println("华为系列产品");
        //华为产品工厂实例
        IProductFactory huaweiProductFactory = new HuaweiProductFactory();
        //生产华为路由器
        IRouterProduct huaweiRouter = huaweiProductFactory.produceRouter();
        huaweiRouter.start();
        huaweiRouter.setting();
        huaweiRouter.openWifi();
        huaweiRouter.shutdown();
        //生产华为手机
        IPhoneProduct huaweiPhone = huaweiProductFactory.produceTelPhone();
        huaweiPhone.start();
        huaweiPhone.callUp();
        huaweiPhone.sendSMS();
        huaweiPhone.shutdown();
    }
}

abstract class AbstractFactory{
   // public abstract Shape getShape(String shapeType);
}
/*
class ShapeFactory extends AbstractFactory {
    @Override
    public Shape getShape(String shapeType) {
        if(StringUtils.isEmpty(shapeType)){
            return null;
        }
        if(shapeType.equalsIgnoreCase("CIRCLE")){
            return new Circle();
        }else if(shapeType.equalsIgnoreCase("RECTANGLE")){
            return new Rectangle();
        } else if(shapeType.equalsIgnoreCase("SQUARE")){
            return new Square();
        }
        return null;
    }
}
class AbstractFactoryProducer {
    public static void main(String[] args){
        AbstractFactory factory= AbstractFactoryProducer.getFactory();
        Shape shape= factory.getShape("CIRCLE");
    }
    public static AbstractFactory getFactory(){
            return new ShapeFactory();

    }
}
*/