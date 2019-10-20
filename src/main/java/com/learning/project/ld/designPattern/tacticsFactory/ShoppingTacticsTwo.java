package com.learning.project.ld.designPattern.tacticsFactory;

/**
 * 策略模式
 * 优点：
 *  解耦
 *  简化了单元测试
 *  --迪米特法则、接口隔离原则 依赖倒置
 *  开闭原则
 * 缺点：
 *  客户端必须知道所有的策略，并自行决定使用哪一个策略
 *  代码中会产生非常多的策略类，增加维护难度
 * @author lvdong
 */
public class ShoppingTacticsTwo {
    public static void main(String[] args){
        CashContext cashContext = new CashContext("正常收费");
        CashContext cashContextTwo = new CashContext(new CashReturn());
        double total=0;
        double count = cashContext.getResult(1.1*2);
        total+=count;
    }
}

/**
 * 算法接口
 */
interface StrategyOne{
    /**
     * 算法方法
     */
    public void algorithmInterface();
}
class Add implements StrategyOne{

    @Override
    public void algorithmInterface() {

    }
}

/**
 * 算法策略配置类
 */
class CashContext{
    private CashSuper cashSuper;
    public CashContext(CashSuper cashSuper){
        this.cashSuper = cashSuper;
    }
    public CashContext(String type){
        switch (type){
            case "正常收费":
                cashSuper = new CashNormal();
                break;
            case "满300减100":
                cashSuper = new CashReturn();
                break;
        }
    }
    public double getResult(double money){
        return cashSuper.acceptCash(money);
    }
}
/**
 * 收费策略接口
 */
interface CashSuper{
    public double acceptCash(double money);
}

/**
 * 满减活动算法
 */
class CashReturn implements CashSuper{
    /**
     * 具体算法
     * @param money
     * @return
     */
    @Override
    public double acceptCash(double money) {
        return money;
    }
}

/**
 * 正常收费
 */
class CashNormal implements CashSuper{

    @Override
    public double acceptCash(double money) {
        return money;
    }
}