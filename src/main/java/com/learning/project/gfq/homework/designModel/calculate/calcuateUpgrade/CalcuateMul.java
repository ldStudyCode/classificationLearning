package com.learning.project.gfq.homework.designModel.calculate.calcuateUpgrade;

public class CalcuateMul implements CalculateFactory {
    @Override
    public double calculate(double num1, double num2) {
        return num1 * num2;
    }
}
