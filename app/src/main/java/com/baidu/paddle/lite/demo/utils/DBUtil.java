package com.baidu.paddle.lite.demo.utils;

import android.util.Log;

import com.baidu.paddle.lite.demo.dao.DataDao;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;

public class DBUtil {

    //连接数据库的工具类
    //MySQL作用是为了提供Java连接MySQL的能力--数据库加载驱动
    //创建数据库连接对象
    public  static Connection conn;
    private static final String TAG = "MysqlWxy";
    public  static Connection getConnection(){

        //加载数据库驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://43.143.108.99:3306/demo?serverTimezone=UTC&UserSSL=false";
            //获取数据库地址，访问数据库的信息，包含url,username,password
            conn = DriverManager.getConnection(url,"wxy","123456");


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    //关闭数据库  (连接数据库的时候返回结果Result（查询的结果，更新-增加-改变）0或者n)
    //数据声明PrepareStatement    where id = 1 或者条件后面的赋值
    //连接对象Conn

    public static void closeConnection(Connection conn){
        try {

            if (conn != null) {
                conn.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.print(DBUtil.getConnection());
        System.out.println(DataDao.getList().get(1).getAccount());
    }
}
