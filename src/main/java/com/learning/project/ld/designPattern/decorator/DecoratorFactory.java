package com.learning.project.ld.designPattern.decorator;

/**
 * 装饰器模式
 *
 */
public class DecoratorFactory {
    public static void main(String[] args){

        Algorithm algorithm =  new Add(new Sub( new AlgorithmImpl()));
        algorithm.count();
    }
}

interface Algorithm{
    public void count();
}
class AlgorithmImpl implements Algorithm{
    public AlgorithmImpl(){
        System.out.println("基础实现类");
    }
    @Override
    public void count() {
        System.out.println("基础方法");
    }
}
class Add implements Algorithm{
    private Algorithm algorithm;
    public Add(Algorithm algorithm){
        this.algorithm = algorithm;
    }
    @Override
    public void count() {
        algorithm.count();
        System.out.println("加法类");
    }
}
class Sub implements Algorithm{
    private Algorithm algorithm;
    public Sub(Algorithm algorithm){
        this.algorithm = algorithm;
    }
    @Override
    public void count() {
        algorithm.count();
        System.out.println("减法类");
    }
}