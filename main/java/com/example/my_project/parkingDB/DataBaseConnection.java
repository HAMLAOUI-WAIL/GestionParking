package com.example.my_project.parkingDB;

import java.sql.*;

public class DataBaseConnection {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 3306;
    private static final String DB_NAME = "databaseparking";
    public static Connection con;
    static {
        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://"+HOST+":"+PORT+"/"+DB_NAME,USERNAME,PASSWORD);
        } catch (SQLException /*| ClassNotFoundException*/ e) {
            System.out.println("Connection faild");
        }
    }
    public static int CheckLogin(String username,String password, String Admin_or_User){
        Connection con = DataBaseConnection.con;
        if (con == null){
            return -1;
        }
        String sql = "SELECT * FROM "+Admin_or_User+" WHERE UserName=? AND Password=?";
        try {
            PreparedStatement Prest = con.prepareStatement(sql);
            Prest.setString(1,username);
            Prest.setString(2,password);
            ResultSet rs = Prest.executeQuery();
            while (rs.next()){
                return 0;
            }

        }catch (SQLException se){
            System.out.println("Connection faild");
        }
        return 1;

    }
    public static int CheckExist(String username){
        Connection con = DataBaseConnection.con;
        if (con == null){
            return -1;
        }
        String sql = "SELECT * FROM `user` WHERE UserName=?";
        try {
            PreparedStatement Prest = con.prepareStatement(sql);
            Prest.setString(1,username);
            ResultSet rs = Prest.executeQuery();
            while (rs.next()){
                return 0;
            }

        }catch (SQLException se){
            System.out.println("Connection faild");
        }
        return 1;

    }
}
