package com.learning.project.gfq.homework.designModel.calcuateUpgrade;

public class CalcuateSub implements CalculateFactory {
    @Override
    public double calculate(double num1, double num2) {
        return  num1 - num2;
    }
}
