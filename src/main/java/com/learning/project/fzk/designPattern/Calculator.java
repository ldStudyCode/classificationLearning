package com.learning.project.fzk.designPattern;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 计算器程序
 * <p>
 * 题目：设计一个加减乘除的计算器，入参：两个数字以及运算符号，出参：计算结果。
 * 使用设计模式进行优化。
 */
public class Calculator {

    static Map<String, Operator> operatorMap = new HashMap<>();

    static {
        operatorMap.put("+", Add.getInstance());
        operatorMap.put("-", new Minus());
        operatorMap.put("*", new Times());
        operatorMap.put("/", new Division());
    }

    /**
     * 测试方法
     */
    @Test
    public void test() {
        System.out.println(calculate(5, "*", 7));
    }

    public static double calculate(double a, String operator, double b) {
        if ((!"+".equals(operator)) && (!"-".equals(operator)) && (!"*".equals(operator)) && (!"/".equals(operator))) {
            throw new IllegalArgumentException("operator error:" + operator);
        }
        return operatorMap.get(operator).calculate(a, b);
    }
}

// 运算符接口，加减乘除操作均继承此接口
interface Operator {
    double calculate(double a, double b);
}

// 加
class Add implements Operator {
    private static Add add = new Add();
    private Add(){}
    public static Add getInstance(){
        return add;
    }
    public double calculate(double a, double b) {
        return a + b;
    }
}

// 减
class Minus implements Operator {
    public double calculate(double a, double b) {
        return a - b;
    }
}

// 乘
class Times implements Operator {
    public double calculate(double a, double b) {
        return a * b;
    }
}

// 除
class Division implements Operator {
    public double calculate(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("divide by zero!");
        }
        return a / b;
    }
}