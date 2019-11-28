package com.learning.project.ld.database;

import com.learning.project.ld.mybatis.JDBCConnection;
import scala.Console;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数据库连接池
 */
public class DataBasePool {
    static LinkedList<Object> connPool = new LinkedList<>();
    static{
        while(connPool.size()<10){
            Object conn = createConn();
            if(null!=conn){
                connPool.add(conn);
            }
        }
        System.out.println("初始化数据库连接池大小："+connPool.size());
    }

    private static Object createConn(){
        Object conn = null;
        //Class.forName("com.mysql.jdbc.Driver");
        String url="";
        String user="";
        String password="";
        conn = new Object();
        return conn;
    }

    /**
     * 获取数据库连接
     * 错误实现代码
     * @return
     */
    public static Object getConn(long timeOut){
        Object conn=null;
        if(timeOut<0){
            while(connPool.size()>0){
                synchronized (connPool){
                    if(connPool.size()>0){
                        conn = connPool.getFirst();
                        return conn;
                    }
                }
            }
        }else {
            long thisTime = System.currentTimeMillis();
            long latencyTime = timeOut;
            long totalTime = System.currentTimeMillis()+latencyTime;
            while(connPool.size()>0){
                synchronized (connPool){
                    if(connPool.size()>0&&latencyTime>0){
                        System.out.println("获取到锁have latencyTime");
                        conn = connPool.getFirst();
                        return conn;
                    }else if(latencyTime>0){
                        latencyTime = totalTime - thisTime;
                        try {
                            System.out.println(latencyTime);
                            connPool.wait(latencyTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        return conn;
                    }
                }
            }
        }
        return conn;
    }
    public static Object getConnVerTwo(long timeOut){
        Object conn = null;
        synchronized (connPool){
            if(timeOut<0){
                while(connPool.isEmpty()){
                    try {
                        connPool.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                conn =  connPool.removeFirst();
            }else{
                //超时时间
                long latencyTime = timeOut;
                long totalTime = timeOut+System.currentTimeMillis();
                while(connPool.isEmpty()&&latencyTime>0){
                    try {
                        connPool.wait(latencyTime);
                        latencyTime = totalTime - System.currentTimeMillis();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(!connPool.isEmpty()){
                    conn = connPool.removeFirst();
                }
            }
            return conn;
        }
    }

    /**
     * 归还数据库连接
     * @param conn
     */
    public static void releaseConn(Object conn){
        synchronized (connPool){
            connPool.add(conn);
            connPool.notifyAll();
        }
    }
    static CountDownLatch countDownLatch ;
    static AtomicInteger getCount = new AtomicInteger();
    static AtomicInteger unGetCount = new AtomicInteger();
    public static void main(String[] args) throws InterruptedException {
        /*有误

        for(int i=0;i<1000;i++){
            new Thread(new FetchJoin()).start();
            countDownLatch.countDown();
        }
        Thread.sleep(5000);
        System.out.println("获取连接次数"+getCount);
        System.out.println("未获取连接次数"+unGetCount);*/
        //线程数
        int threadCount = 50;
        countDownLatch = new CountDownLatch(threadCount);
        int count = 20;//每个线程操作次数
        for(int i =0;i<threadCount;i++){
            new Thread(new FetchJoin(count)).start();
        }
        countDownLatch.await();
        System.out.println("线程总尝试次数"+(threadCount*count));
        System.out.println("拿到连接次数："+getCount);
        System.out.println("未拿到连接次数："+unGetCount);
    }
    private static class FetchJoin implements Runnable{
        int count;
        public FetchJoin(int count){
            this.count = count;
        }
        @Override
        public void run() {
            while(count>0){
                try{
                    Object conn = getConn(-1);
                    if(conn!=null){
                        try{

                        } finally {
                            connPool.add(conn);
                            getCount.incrementAndGet();
                        }
                    }else{
                        unGetCount.incrementAndGet();
                        System.out.println(Thread.currentThread().getName()+"等待超时！");
                    }
                }finally {
                    count--;
                }

            }
            countDownLatch.countDown();
        }
    }
}

