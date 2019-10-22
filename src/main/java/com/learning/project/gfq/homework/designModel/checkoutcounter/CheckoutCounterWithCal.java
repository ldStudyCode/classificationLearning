package com.learning.project.gfq.homework.designModel.checkoutcounter;

public abstract class CheckoutCounterWithCal implements CheckoutCounter{

    /*
    abstract层作用，把算钱的逻辑和打折的逻辑分层，算钱的逻辑统一放在底层
    这样做算钱的逻辑，打折如何作用于算钱的逻辑，都可以一次性更改，且方便调

    添加其他折扣，继承此类进行添加即可
    */
    @Override
    public double calculateMoney(double price, int num) {
        return outlets(price * num);
    }
}
