package com.learning.project.gfq.homework.designModel.checkoutcounter;

public class ZheKou extends CheckoutCounterWithCal{

    private static final double ZheKou_7 = 0.7;

    @Override
    public double outlets(double sumMoney) {
        return sumMoney * ZheKou_7;
    }

}
