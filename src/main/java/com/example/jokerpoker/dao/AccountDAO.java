package com.example.jokerpoker.dao;

import com.example.jokerpoker.util.DBConnection;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

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

    public void saveUser(String user, String pass) throws SQLException {
        Connection conn = DBConnection.getConnect();
        String sql = "insert into User (username, password) values (?, ?)";

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



}
