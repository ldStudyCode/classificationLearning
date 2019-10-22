package com.learning.project.gfq.homework.designModel.calcuateUpgrade;

import java.awt.*;

public class CalculateUpgrade {

    public static void main(String[] args) {
        CalculateFactory calcuate = new CalculateAdd();
        System.out.println(calcuate.calculate(1, 1));
    }

}
