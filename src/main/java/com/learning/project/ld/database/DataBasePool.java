package com.learning.project.ld.database;

import com.learning.project.ld.mybatis.JDBCConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * 数据库连接池
 */
public class DataBasePool {
    static LinkedList<Connection> connPool = new LinkedList<>();

    /**
     * 初始化数据库连接池大小,现固定为10
     */
    private DataBasePool(){
        while(connPool.size()<10){
            Connection conn = createConn();
            if(null!=conn){
                connPool.add(conn);
            }
        }
    }
    private static Connection createConn(){
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url="";
            String user="";
            String password="";
            conn = DriverManager.getConnection(url,user,password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 获取数据库连接
     * @return
     */
    public static Connection getConn(long timeOut){
        synchronized (connPool){
            if(timeOut<0){
                while(connPool.size()>0){
                    return connPool.getFirst();
                }
            }else{

            }

        }
    }

    /**
     * 归还数据库连接
     * @param conn
     */
    public void releaseConn(Connection conn){
        synchronized (connPool){
            connPool.add(conn);
        }
    }
}
