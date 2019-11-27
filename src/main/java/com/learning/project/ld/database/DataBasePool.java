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

    /**
     * 初始化数据库连接池大小,现固定为10
     */
    private DataBasePool(){
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
    static CountDownLatch countDownLatch = new CountDownLatch(1000);
    static AtomicInteger getCount = new AtomicInteger();
    static AtomicInteger unGetCount = new AtomicInteger();
    public static void main(String[] args) throws InterruptedException {
        DataBasePool dataBasePool = new DataBasePool();
        for(int i=0;i<1000;i++){
            new Thread(new FetchJoin()).start();
            countDownLatch.countDown();
        }
        Thread.sleep(5000);
        System.out.println("获取连接次数"+getCount);
        System.out.println("未获取连接次数"+unGetCount);
    }
    private static class FetchJoin implements Runnable{
        @Override
        public void run() {
            try {
                countDownLatch.await();
                Thread.sleep((long) (Math.random()*5+1));
                Object conn = getConn(-1);
                if(conn==null){
                    unGetCount.getAndIncrement();
                }else{
                    releaseConn(conn);
                    getCount.getAndIncrement();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

