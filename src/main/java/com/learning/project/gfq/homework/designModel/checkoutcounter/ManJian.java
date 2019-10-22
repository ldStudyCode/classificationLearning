package com.learning.project.gfq.homework.designModel.checkoutcounter;

public class ManJian extends CheckoutCounterWithCal {

    private static final double ManJian_100    = 100;
    private static final double ManJian_100_50 = 50;
    @Override
    public double outlets(double sumMoney) {
        return sumMoney >= ManJian_100?sumMoney - ManJian_100_50:sumMoney;
    }
}
