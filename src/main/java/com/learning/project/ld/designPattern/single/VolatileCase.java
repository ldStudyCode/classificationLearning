package com.learning.project.ld.designPattern.single;


import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 类说明：演示Volatile的提供的可见性
 */
public class VolatileCase {
    private static AtomicBoolean ready = new AtomicBoolean(false);
    private  static  TestBoolean testBoolean = new TestBoolean();
    private static int number;


    //
    private static class PrintThread extends Thread{
        @Override
        public void run() {
            System.out.println("PrintThread is running.......");
            System.out.println(ready.get());
            while(!ready.get()){number=1;}//无限循环
            System.out.println("number = "+number);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new PrintThread().start();
        Thread.sleep(1000);
        number = 51;
//        System.out.println(testBoolean);
        ready.set(true);
//        testBoolean.ready = true;
        Thread.sleep(5000);
        System.out.println("main is ended!");
    }
}

class TestBoolean{
    public  boolean ready;
}
