package com.learning.project.ld.designPattern.tacticsFactory;

/**
 * 策略模式
 */
public class TacticsFactory {
    public static void main(String[] args){
        Context context = new Context(new ConcreteStrategyA());
        context.contextInterface();
        context = new Context(new ConcreteStrategyB());
        context.contextInterface();
        context = new Context(new ConcreteStrategyC());
        context.contextInterface();
    }
}
interface  Strategy{
    public  void algorithmInterface();
}

/**
 * 代理类
 */
class Context{
    Strategy strategy;
    public Context(Strategy strategy){
        this.strategy=strategy;
    }
    public void contextInterface(){
        strategy.algorithmInterface();
    }
}

class ConcreteStrategyA implements Strategy{

    @Override
    public void algorithmInterface() {
        System.out.println("算法A的实现");
    }
}
class ConcreteStrategyB implements Strategy{

    @Override
    public void algorithmInterface() {
        System.out.println("算法B的实现");
    }
}

class ConcreteStrategyC implements Strategy{

    @Override
    public void algorithmInterface() {
        System.out.println("算法C的实现");
    }
}
