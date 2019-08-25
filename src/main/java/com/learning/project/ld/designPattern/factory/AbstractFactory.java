package com.learning.project.ld.designPattern.factory;

import org.thymeleaf.util.StringUtils;

/**
 * 抽象工厂类
 * @author lvdong
 */

/**
 * 为形状创建一个接口
 */
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
}


abstract class AbstractFactory{
    public abstract Shape getShape(String shapeType);
}
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