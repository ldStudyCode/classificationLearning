package com.learning.project.gfq.homework.designModel.calculate.calcuateold;

import org.springframework.scheduling.support.SimpleTriggerContext;

import java.util.HashSet;
import java.util.Set;

public class Calculate {

    public static void main(String[] args) {
        System.out.println(calculate(1.1,2.3,OPERATOR.PLUS));
    }

    /*
    设计一个加减乘除的计算器，在控制台输入两个数字以及运算符号，打印计算结果
    */
    public enum OPERATOR {
        PLUS("+"), SUB("-"), MULTI("*"), DIVIDE("/");
        private final String operator;
        private OPERATOR(String operator) {
            this.operator = operator;
        }
        public String getOperator() {
            return operator;
        }
        public static boolean isIncluded(OPERATOR operator) {
            boolean isIncluded = false;
            for (OPERATOR each: OPERATOR.values()) {
                if (each.equals(operator)) {
                    isIncluded = true;
                    break;
                }
            }
            return isIncluded;
        }
    }
    public static double calculate(double num_1, double num_2, OPERATOR operator) {
        if (!OPERATOR.isIncluded(operator))
            throw new IllegalArgumentException("非法运算符号！");
        switch (operator) {
            case PLUS:
                return num_1 + num_2;
            case SUB:
                return num_1 - num_2;
            case MULTI:
                return num_1 * num_2;
            case DIVIDE:
                return num_1 /num_2;
        }
        throw  new IllegalArgumentException("系统未知错误");
    }
}
