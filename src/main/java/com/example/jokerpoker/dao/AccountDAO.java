package com.example.jokerpoker.dao;

import com.example.jokerpoker.util.DBConnection;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;

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

    public static void main(String[] args) {
        AccountDAO accountDAO = new AccountDAO();
        int n = accountDAO.checkUser("player2","123456");
        System.out.println(n);
    }
}
