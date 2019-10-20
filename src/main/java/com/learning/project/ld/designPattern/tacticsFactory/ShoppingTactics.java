package com.learning.project.ld.designPattern.tacticsFactory;

/**
 * 需求 做一个商场收银软件，营业员根据客户所购买商品的单价和数量，向客户收费
 * 商品
 * 策略模式
 */
public class ShoppingTactics {
    public static void main(String[] args){
        double total=0;
        total+=new CountShopping().count(new ShoppingCashier(1.1,1.1,""));
    }
}

/**
 * 计算类
 */
class CountShopping{

    public double count(ShoppingCashier shoppingCashier){
        return shoppingCashier.price*shoppingCashier.num*shoppingCashier.discount;
    }
}

/**
 * 使用次类型问题：
 *  新增满减无法解决 例如满300减200
 */
interface ShoppingAlgorithm{
    public double getResult(double countMoney);
}

/**
 * 折扣子类
 */
class discountAlgorithm implements ShoppingAlgorithm{
    double discount;
    public discountAlgorithm(double discount){
        this.discount=discount;
    }
    @Override
    public double getResult(double countMoney) {
        return countMoney*discount;
    }
}

/**
 * 满减子类
 */
class FullReduction implements ShoppingAlgorithm{
    double numOne;
    double numTwo;
    public FullReduction(double numOne,double numTwo){
        this.numOne=numOne;
        this.numTwo=numTwo;
    }
    @Override
    public double getResult(double countMoney) {
        if(countMoney>=numOne){
            return countMoney-numTwo;
        }
        return countMoney;
    }
}

class ShoppingActivityFactory{
    public ShoppingAlgorithm getShoppingAlgorithm(String type){
        ShoppingAlgorithm shoppingAlgorithm=null;
        switch(type){
            case "1":
                shoppingAlgorithm =  new discountAlgorithm(0.8);

        }
        return shoppingAlgorithm;
    }
}

/**
 * 数据存储类
 */
class ShoppingCashier{
    Double price;
    Double num;
    String type;
    Double discount;
    public ShoppingCashier(Double price,Double num,String type){
        this.price=price;
        this.num=num;
        switch (type){
            case "正常收费":
                discount=1.0;
                break;
            case "打八折":
                discount=0.8;
                break;
            case "打五折":
                discount=0.5;
                break;
        }
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }
}
