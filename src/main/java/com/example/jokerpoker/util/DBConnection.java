package com.example.jokerpoker.util;

import java.sql.*;

public final class DBConnection {
    static public  Connection getConnect(){
        Connection cn = null;
//        String url = "jdbc:mysql://192.168.45.108:3306/game";
        String url = "jdbc:mysql://localhost:3306/game";
        String userName = "root",password="123456";
        //connect to mysql driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            cn = DriverManager.getConnection(url,userName,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cn;
    }
    public static void main(String[] args) {
        Connection cn= getConnect();
        System.out.println(cn);
    }
}
