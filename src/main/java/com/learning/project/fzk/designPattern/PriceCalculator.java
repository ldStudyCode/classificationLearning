package com.learning.project.fzk.designPattern;

import org.junit.Test;
import org.testng.collections.Lists;

import java.util.List;

/**
 * 作业：实现一个商场收银系统
 * （单价、数量，计算总和），考虑商场可能会出现活动 例如 打折 满减（目前只考虑总价满减或打折）
 * <p>
 * 注：所有价格字段(long类型)的单位都是分
 */
public class PriceCalculator {
    /**
     * 测试方法
     */
    @Test
    public void test() {
        CartItem item1 = new CartItem(100, 1);
        CartItem item2 = new CartItem(40, 3);
        Cart cart = new Cart(Lists.newArrayList(item1, item2));

        System.out.println(calculate(cart, new Reduce(200, 10)));
    }

    /**
     * 计算购物车促销后的总价
     *
     * @param cart     购物车
     * @param activity 促销活动
     * @return
     */
    public long calculate(Cart cart, Activity activity) {
        if (activity == null) {
            return cart.calcOriginPrice();
        }
        return activity.calculate(cart);
    }
}

/**
 * 购物车
 */
class Cart {
    // 商品信息
    List<CartItem> items;

    public Cart(List<CartItem> items) {
        this.items = items;
    }

    // 计算折扣前的价格
    long calcOriginPrice() {
        if (items == null || items.size() == 0) {
            return 0l;
        }
        long result = 0l;
        for (CartItem item : items) {
            result += item.count * item.price;
        }
        return result;
    }
}

/**
 * 购物车单品实体
 */
class CartItem {
    // 单价
    long price;
    // 数量
    int count;

    public CartItem(long price, int count) {
        this.price = price;
        this.count = count;
    }
}

/**
 * 促销活动接口
 * 打折、满减等促销类，都实现此接口
 */
interface Activity {
    long calculate(Cart cart);
}

/**
 * 打折
 */
class Discount implements Activity {

    // 折扣率，0-1
    private double rate;

    public Discount(double rate) {
        this.rate = rate;
    }

    @Override
    public long calculate(Cart cart) {
        return (long) (cart.calcOriginPrice() * rate);
    }
}

/**
 * 满减
 */
class Reduce implements Activity {

    // 满多少
    private long least;
    // 减多少
    private long reduce;

    public Reduce(long least, long reduce) {
        this.least = least;
        this.reduce = reduce;
    }

    @Override
    public long calculate(Cart cart) {
        long originPrice = cart.calcOriginPrice();
        if (originPrice >= least) {
            return originPrice - reduce;
        }
        return originPrice;
    }
}