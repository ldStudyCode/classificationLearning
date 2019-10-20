package com.learning.project.ld.designPattern.factory;

/**
 * 工厂模式
 * 优点克服了简单工厂违反的开闭原则，保持了封装对象创建过程的优点
 * @author lvdong
 */
public class FactoryModel {
    public static void main(String[] args){
        CreateAnimalFactory createAnimalFactory = new CreateBuleCatFactory();
        Animal animal =createAnimalFactory.createAnimal();
        animal.run();
    }
}
interface Animal{
    public void run();
}
interface CreateAnimalFactory{
    public Animal createAnimal();
}
class BuleCat implements Animal{
    @Override
    public void run() {
        System.out.println("cat run");
    }
}
class Dog implements Animal{

    @Override
    public void run() {
        System.out.println("dog run");
    }
}

class CreateBuleCatFactory implements CreateAnimalFactory{
    @Override
    public Animal createAnimal() {
        return new BuleCat();
    }
}
class CreateDogFactory implements CreateAnimalFactory{

    @Override
    public Animal createAnimal() {
        return new Dog();
    }
}

