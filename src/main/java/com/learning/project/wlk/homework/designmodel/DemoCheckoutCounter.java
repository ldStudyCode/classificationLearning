package com.learning.project.wlk.homework.designmodel;

import java.util.ArrayList;
import java.util.List;

public class DemoCheckoutCounter {
    public static void main(String[] args) {
        CheckoutCounter counter = new CheckoutCounter();

        Discount discount1 = new OverMinDiscount(99.0f,10f);
        Discount discount2 = new OverMinDiscount(500.0f,100f);
        Discount discount3 = new OverMinDiscount(999.0f,200f);
        Discount discount4 = new PercentageDiscount(0.9f);
        Discount discount5 = new PercentageDiscount(0.8f);
        Discount discount6 = new PercentageDiscount(0.75f);

        counter.Receipt(new Order(10,12.0f,discount1,discount2,discount5));
        counter.Receipt(new Order(3,123.32f,discount2,discount4));
        counter.Receipt(new Order(3,3243.23f,discount4,discount2,discount1,discount2));
        counter.Receipt(new Order(4,234.23f,discount5,discount1,discount3));
        counter.Receipt(new Order(2,2342.2342f,discount3,discount2,discount4));
        System.out.println("一共支付了 "+counter.getTotalOrderCount()+"比订单");
        System.out.println("一共购买了 "+counter.getTotalGoodCount()+" 个商品");
        System.out.println("一共应付了 "+counter.getTotalOrderBillPrice()+ " 金额");
        System.out.println("一共实付了 "+counter.getTotalOrderActualPrice()+" 金额");



    }
}




class CheckoutCounter{
    private int totalGoodCount;

    private int totalOrderCount ;
    private float totalOrderBillPrice ;
    private float totalOrderActualPrice;
    public  CheckoutCounter(){
        totalOrderCount = 0;
        totalOrderActualPrice = 0.0f;
        totalOrderBillPrice = 0.0f;
    }
    public void Receipt(Order order){
        totalGoodCount+=order.count;
        totalOrderCount++;
        totalOrderBillPrice+=order.billPrice;
        totalOrderActualPrice+=order.mustPay();
    }

    public float getTotalOrderActualPrice() {
        return totalOrderActualPrice;
    }

    public float getTotalOrderBillPrice() {
        return totalOrderBillPrice;
    }

    public int getTotalOrderCount() {
        return totalOrderCount;
    }

    public int getTotalGoodCount() {
        return totalGoodCount;
    }
}

class Order{
    public int count;
    public float singlePrice;
    public float billPrice;
    public float actualPrice ;
    public float discountPrice;
    public List<Discount> discounts;
    public Order(int count,float singlePrice,Discount ...manyDiscounts){
        this.count = count;
        this.singlePrice = singlePrice;

        this.billPrice = count*singlePrice;
        actualPrice = .0f;
        discounts = new ArrayList<>();
        for(Discount dis:manyDiscounts){
            addDiscounts(dis);
        }
    }
    public float mustPay(){
        for(Discount discount : discounts){
            discountPrice+=discount.doDiscount(this);
        }
        actualPrice = billPrice-discountPrice;
        return actualPrice;
    }
    public void addDiscounts(Discount discount){
        discounts.add(discount);

    }
}

interface Discount{
    public float doDiscount(Order order);
}

class OverMinDiscount implements Discount{
    private float overPrice;
    private  float minusPrice;
    public OverMinDiscount(float overPrice,float minusPrice){
        this.overPrice = overPrice;
        this.minusPrice = minusPrice;
    }

    @Override
    public float doDiscount(Order order) {
        if(order.billPrice >= overPrice) {
            return minusPrice;
        }else {
            return 0f;
        }
    }
}

class PercentageDiscount implements  Discount{
    /**
     * 打折的折数,
     * 范围为浮点数[0.0,1.0)：则为折数
     * 范围为浮点数[1.0,1pp)：则为口头的折数，，85折即为0.85
     *
     */

    private  float percentage;
    public PercentageDiscount(float pPercentage){
        if(pPercentage>=1){
            this.percentage = pPercentage/100;
        }else {
            this.percentage = pPercentage;
        }
    }

    @Override
    public float doDiscount(Order order) {
        return order.billPrice*(1-percentage);
    }
}
