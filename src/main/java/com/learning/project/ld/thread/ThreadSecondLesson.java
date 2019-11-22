package com.learning.project.ld.thread;

/**
 * @author lvdong
 * date 2019-11-21
 * 多线程第二节课
 * volatile最轻量的同步机制
 */
public class ThreadSecondLesson {
    public static  boolean ready;
    public static int num;
    public static void main(String[] args) throws InterruptedException {
        VolatileTest volatileTest = new VolatileTest();
        volatileTest.start();
        ready=true;
        num = 51;
        Thread.sleep(5000);
        System.out.println("main run end");
    }
   private static class VolatileTest  extends Thread{
        @Override
        public void run() {
            System.out.println("子线程running");
            while(!ready);
            System.out.println("检测状态修改:"+num);
        }
    }
}

