package com.learning.project.ld.designPattern.policy;

import java.math.BigDecimal;

/**
 *
 */
public class PolicyFactory {
    public static void main(String[] args){
        BigDecimal result ;
        Goods goods = new Goods();
        goods.price=new BigDecimal(2);
        goods.num = 1;
        goods.discount = new BigDecimal(0.8);
        BigDecimal resultBig = goods.count(goods);
        BigDecimal resultBigTwo = goods.count(goods);
        result = resultBigTwo.add(resultBig);
        System.out.println(result.doubleValue());

    }
}

/**
 * 商品类
 */
class Goods{
    Integer num;
    BigDecimal price;
    BigDecimal discount;

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal count(Goods goods){
        return goods.price.multiply(new BigDecimal(goods.num)).multiply(goods.discount);
    }
}

interface  Activity{
    public BigDecimal getVal(BigDecimal countNum);
}
/**
 * 商场活动折扣类
 */
class ShoppingMalls implements Activity{
    BigDecimal discount;

    @Override
    public BigDecimal getVal(BigDecimal countNum) {

        return null;
    }
}

/**
 * 满减类
 */
class FullReduction implements Activity{
    Integer one;Integer two;
    public FullReduction(Integer one,Integer two){
        if(one<two){
            try {
                throw new Exception("满减值输入错误");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.one=one;
        this.two=two;
    }
    @Override
    public BigDecimal getVal(BigDecimal countNum) {
        if(countNum.doubleValue()>=one){
            return countNum.subtract(new BigDecimal(two));
        }
        return countNum;
    }

}
/**
 * 商场活动的简单工厂
 */
class ShoppingMallsFactory{

}