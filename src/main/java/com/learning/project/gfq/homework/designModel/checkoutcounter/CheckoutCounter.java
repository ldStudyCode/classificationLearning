package com.learning.project.gfq.homework.designModel.checkoutcounter;

public interface CheckoutCounter {
    //作业：实现一个商场收银系统  （单价、数量，计算总和），
    //考虑商场可能会出现活动 例如 打折 满减（目前只考虑总价满减或打折），可优先实现打折的方式 在考虑增加满减
    public double calculateMoney(double price, int num);
    public double outlets(double sumPrice);

}
