package com.learning.project.ld.mybatis;

import java.sql.*;

/**
 * 使用原生的jdbc链接数据库
 */
public class JDBCConnection {
    /**
     * 原生jdbc连接数据库
     * 步骤过于复杂 需要执行连接数据库5大步
     * 数据库多次连接关闭，导致数据库资源的利用浪费
     * 重复性代码较多，遍历值维护成本极高。
     * 日志代码与业务代码耦合
     * 没有统一的异常处理机制
     * 事务需自行管理
     * @param args
     */
    public static void main(String[] args){
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs = null;
        try {
            //加载驱动，根据不同的加载去访问不同的数据库
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://192.168.31.102:3306/study?characterEncoding=UTF-8&serverTimezone=UTC";
            String user="root";
            String password="";
            //根据数据库连接信息获取数据库连接
            conn= DriverManager.getConnection(url,user,password);
            String sql="select * from study_mybatis_reflect";
            ps = conn.prepareStatement(sql);
            //ResultSet rs = ps.execute();
            //execute方法 会返回true 以及false 只是代表是否有值
            rs = ps.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString(1));
                System.out.println(rs.getString("name"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {

                try {
                    if(null==rs)
                        rs.close();
                    if(null==ps)
                        ps.close();
                    if(null==conn)
                        conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

        }
    }
}
