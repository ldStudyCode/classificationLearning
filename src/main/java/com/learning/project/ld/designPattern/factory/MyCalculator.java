package com.learning.project.ld.designPattern.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.util.StringUtils;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * 计算器控制台输出，要求输入两个数和运算符，得到计算结果
 */
public class MyCalculator {
    static Logger logger = LoggerFactory.getLogger(MyCalculator.class);
    public static void main(String args[]){
         System.out.println(count(getParam()));
        CountFactory cf=new CountFactory();
        CountParam countParam = new CountParam();
        cf.getResult(countParam,"+");
    }
    public static CountParam  getParam(){
        CountParam countParam = new CountParam();
        logger.info("请输入第一个数值");
        Scanner scanner=new Scanner(System.in);
        String numOneStr=scanner.nextLine();
        if(StringUtils.isEmpty(numOneStr))
            logger.info("输入第一个数值有误");
        else{
            try{
                countParam.setNumOne(new BigDecimal(Double.valueOf(numOneStr)));
            }catch(Exception e){
                logger.info("输入第一个数值有误");
            }
        }
        logger.info("请输入第二个数值");
        scanner=new Scanner(System.in);
        String numTwoStr=scanner.nextLine();
        if(StringUtils.isEmpty(numTwoStr))
            logger.info("输入第二个数值有误");
        else{
            try{
                countParam.setNumTwo(new BigDecimal(Double.valueOf(numTwoStr)));
            }catch(Exception e){
                logger.info("输入第二个数值有误");
            }
        }
        logger.info("请输入运算符");
        scanner=new Scanner(System.in);
        String operator=scanner.nextLine();
        if(!operaTorYesOrNo(operator))
            logger.info("请输入正确的运算符运算符");
        else
            countParam.setOperator(operator);
        return countParam;
    }
    public static Boolean operaTorYesOrNo(String operator){
        Boolean checkResult=false;
        switch(operator){
            case "+":
                checkResult=true;
                break;
            case "-":
                checkResult=true;
                break;
            case "*":
                checkResult=true;
                break;
            case "/":
                checkResult=true;
                break;
        }
        return checkResult;

    }

    /**
     * 此方法每次新增都需要进行重新编译，而加减乘除方法都需要进行重新编译
     * @param countParam
     * @return
     */
    public static Double count(CountParam countParam){
        Double resultNumber=null;
        switch(countParam.getOperator()){
            case "+":
                resultNumber=countParam.getNumOne().add(countParam.getNumTwo()).doubleValue();
                break;
            case "-":
                resultNumber=countParam.getNumOne().subtract(countParam.getNumTwo()).doubleValue();
                break;
            case "*":
                resultNumber=countParam.getNumOne().multiply(countParam.getNumTwo()).doubleValue();
                break;
            case "/":
                resultNumber=countParam.getNumOne().divide(countParam.getNumTwo()).doubleValue();
                break;
        }
        return resultNumber;
    }
}

/**
 * 算法接口
 */
interface CountClass{

    Double getResult();
}

/**
 * 加法类
 */
class AddCount implements CountClass{
    private CountParam countParam;
    public AddCount(CountParam countParam) {
        this.countParam=countParam;
    }
    @Override
    public Double getResult() {
        return countParam.getNumOne().add(countParam.getNumTwo()).doubleValue();
    }
}

/**
 * 配置类用来管理算法类
 *
 */
class SubCount implements CountClass{
    private CountParam countParam;
    public SubCount(CountParam countParam) {
        this.countParam=countParam;
    }
    @Override
    public Double getResult() {
        return countParam.getNumOne().subtract(countParam.getNumTwo()).doubleValue();
    }
}

class CountFactory {
    public CountClass getResult(CountParam countParam,String type) {
        CountClass cc=null;
        switch(type){
            case "+":
                cc=new AddCount(countParam);
                break;
            case "-":
                cc=new SubCount(countParam);
                break;
            case "*":
                cc=new AddCount(countParam);
                break;
            case "/":
                cc=new SubCount(countParam);
                break;
        }
        return null;
    }
}


/**
 * 计算值存储类
 */
class CountParam{
    BigDecimal numOne;
    BigDecimal numTwo;
    String operator;

    public BigDecimal getNumOne() {
        return numOne;
    }

    public void setNumOne(BigDecimal numOne) {
        this.numOne = numOne;
    }

    public BigDecimal getNumTwo() {
        return numTwo;
    }

    public void setNumTwo(BigDecimal numTwo) {
        this.numTwo = numTwo;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}