package org.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {

    protected static String url = "jdbc:mysql://127.0.0.1:3306/library_management";
    protected static String user = "root";
    protected static String password = "Liver1890_";
    public static Connection connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            return con;
        } catch (Exception e) {
            System.out.println("Connection error: " + e.getMessage());
            return null;
        }


    }

}
