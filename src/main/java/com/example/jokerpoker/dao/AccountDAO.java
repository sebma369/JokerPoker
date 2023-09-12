package com.example.jokerpoker.dao;

import com.example.jokerpoker.util.DBConnection;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountDAO {
    public int checkUser(String username,String password) {
        Connection cn = DBConnection.getConnect();
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select * from user where username=? and password=?";
        ResultSetHandler<User> h = new BeanHandler<User>(User.class);
        int n = -1;
        try {
            User p = queryRunner.query(cn,sql, h, username, password);
            if(p!=null) {
                n = 1;
            } else {
                n = -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return n;
    }

    // 定义一个名为checkOnline的方法，用于检测online是否为0
    public boolean checkOnline(String username) throws SQLException {
        Connection conn = DBConnection.getConnect();
        String sql = "select online from user where username=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        boolean result = false;
        if (rs.next()) {
            int online = rs.getInt("online");
            if (online == 0) {
                result = true;
            } else {
                result = false;
            }
        }
        rs.close();
        ps.close();
        conn.close();
        return result;
    }


    public void saveUser(String user, String pass) throws SQLException {
        Connection conn = DBConnection.getConnect();
        String sql = "insert into user (username, password, online) values (?, ?, 0)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, user);
        ps.setString(2, pass);
        int n = ps.executeUpdate();
        if (n > 0) {
            System.out.println("插入成功");
        } else {
            System.out.println("插入失败");
        }
        ps.close();
        conn.close();
    }

    public void updateOnline(String username, int online) throws SQLException {
        Connection conn = DBConnection.getConnect();
        String sql = "update user set online=? where username=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, online);
        ps.setString(2, username);
        ps.executeUpdate();
        ps.close();
        conn.close();
    }
}
