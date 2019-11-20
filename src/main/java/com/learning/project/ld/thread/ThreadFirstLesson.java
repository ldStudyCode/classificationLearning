package com.learning.project.ld.thread;

/**
 * 多线程学习第一节课
 * 线程的创建方式分为两种：接口以及继承
 * Interrupt 线程中断方法
 * sleep 会修改Interrupt 标识位 如若必须中断，在catch里再进行中断
 * 不使用自定义标识，无法在sleep检测到中断
 * 设置为守护线程之后会随着主线程的销毁而销毁，不在守护线程的finally写释放代码，因为守护线程的finally不一定执行
 * join 方法保证线程需在某线程之后执行
 */
public class ThreadFirstLesson {
    public static void main(String[] args){
        SyncTest.syncStatic();
        SyncTest.syncClass();

    }
}

class MyClassThread extends Thread{
    @Override
    public void run() {
        System.out.println("继承方式实现");
    }
}
class MyInterfaceThread implements Runnable{
    @Override
    public void run() {
        System.out.println("接口方式实现");
    }
}
class MyThreadTest extends Thread{
    @Override
    public void run() {
        while(!isInterrupted()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                interrupt();
                System.out.println("线程标识："+isInterrupted());
            }
            System.out.println(Thread.currentThread().getName()+"is running");
        }
        System.out.println(Thread.currentThread().getName()+"线程结束");
    }
}

/**
 * 方法上的锁 类锁 对象锁
 */
class SyncTest{
    private static Object obj = new Object();
    public  static void syncClass(){
        synchronized (obj){
            System.out.println("方法被调用");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public synchronized static void syncStatic(){

        try {
            System.out.println("静态方法被调用");
            Thread.sleep(5000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void intCount(){
        System.out.println("第一个方法被调用");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public synchronized void intCountTwo(){
        System.out.println("第二个方法被调用");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}