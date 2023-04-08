package com.baidu.paddle.lite.demo.dao;

import android.util.Log;

import com.baidu.paddle.lite.demo.bean.Data;
import com.baidu.paddle.lite.demo.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//用户数据库连接类
public class DataDao {

    //新增
    public static boolean add(Data data) {
        String sql = "insert into data(account,actionId,actionCount,maxScore,minScore,aveScore,time)values('" + data.getAccount() + "','" + data.getActionId() + "','" + data.getActionCount()+ "','" + data.getMaxScore()+ "','" + data.getMinScore()+ "','" + data.getAveScore() + "','" + data.getDateTime() + "')";
        Connection conn = DBUtil.getConnection();
        PreparedStatement state = null;
        boolean f = false;
        int a = 0;
        try {
            state = (PreparedStatement) conn.createStatement();
            a = state.executeUpdate(sql);
        } catch (Exception e) {
            Log.e("add->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);
        }
        if (a > 0) {
            f = true;
        }
        return f;
    }

    //删除
    public static boolean delete(Data data) {
        String sql = "delete from data where account=" + data.getAccount();
        Connection conn = DBUtil.getConnection();
        PreparedStatement state = null;
        boolean f = false;
        int a = 0;
        try {
            state = (PreparedStatement) conn.createStatement();
            a = state.executeUpdate(sql);
        } catch (Exception e) {
            Log.e("delete->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);
        }
        if (a > 0) {
            f = true;
        }
        return f;
    }


    //修改
    public static boolean update(Data data) {

        String sql = "update data set " + "account='" + data.getAccount() + "', actionId='" + data.getActionId() + "', actionCount='" + data.getActionCount() + "', maxScore='" + data.getMaxScore() + "' minScore='" + data.getMinScore() + "', maxScore='" + data.getAveScore() +"', maxScore='" + data.getDateTime() +"'";
        Connection conn = DBUtil.getConnection();
        PreparedStatement state = null;
        boolean f = false;
        int a = 0;
        try {
            state = (PreparedStatement) conn.createStatement();
            a = state.executeUpdate(sql);
        } catch (Exception e) {
            Log.e("update->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);
        }
        if (a > 0) {
            f = true;
        }
        return f;
    }

    public static String getAccount(String account) {

        String result = null;
        //MySQL 语句
        String sql = "select account from data where account = " + account;
        Connection conn = DBUtil.getConnection();
        Statement state = null;
        ResultSet rs = null;
        boolean f = false;
        int a = 0;
        try {
            state = conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                result = rs.getString(1);
            }
        } catch (Exception e) {
            Log.e("update->", e.getMessage(), e);
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);
        }
        if (a > 0) {
            f = true;
        }
        return result;
    }

    //获取列表
    public static List<Data> getList() {

        //结果存放集合
        List<Data> list = new ArrayList<>();
        //MySQL 语句
        String sql = "select * from data";
        Connection conn = DBUtil.getConnection();
        Statement state = null;
        ResultSet rs = null;
        boolean f = false;
        int a = 0;
        try {
            state = conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                Data data = new Data();
                data.setAccount(rs.getString(1));
                data.setActionId(rs.getInt(2));
                data.setActionCount(rs.getInt(3));
                data.setMaxScore(rs.getDouble(4));
                data.setMinScore(rs.getDouble(5));
                data.setAveScore(rs.getDouble(6));
                data.setDateTime(rs.getString(7));
                list.add(data);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);
        }
        if (a > 0) {
            f = true;
        }
        return list;
    }


}