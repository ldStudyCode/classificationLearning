package com.learning.project.ld.designPattern.builderFactory;

/**
 * 建造者模式
 *
 */
public class BuilderFactory {
    public static void main(String[] args) {
        Builder builder=new ConcreteBuilder();
        Director director=new Director(builder);
        Product product=director.construct();
        product.show();
    }
}

/**
 * 产品角色：包含多个组成部件的复杂对象
 */
class Product
{
    private String partA;
    private String partB;
    private String partC;
    public void setPartA(String partA)
    {
        this.partA=partA;
    }
    public void setPartB(String partB)
    {
        this.partB=partB;
    }
    public void setPartC(String partC)
    {
        this.partC=partC;
    }
    public void show()
    {
        //显示产品的特性
    }
}

/**
 *  抽象建造者：包含创建产品各个子部件的抽象方法。
 */
abstract class Builder
{
    //创建产品对象
    protected Product product=new Product();
    public abstract void buildPartA();
    public abstract void buildPartB();
    public abstract void buildPartC();
    //返回产品对象
    public Product getResult() {
        return product;
    }
}

/**
 * 具体建造者：实现了抽象建造者接口
 */
class ConcreteBuilder extends Builder {
    public void buildPartA()
    {
        product.setPartA("建造 PartA");
    }
    public void buildPartB()
    {
        product.setPartA("建造 PartB");
    }
    public void buildPartC()
    {
        product.setPartA("建造 PartC");
    }
}

/**
 * 指挥者：调用建造者中的方法完成复杂对象的创建
 */
class Director {
    private Builder builder;
    public Director(Builder builder)
    {
        this.builder=builder;
    }
    //产品构建与组装方法
    public Product construct()
    {
        builder.buildPartA();
        builder.buildPartB();
        builder.buildPartC();
        return builder.getResult();
    }
}