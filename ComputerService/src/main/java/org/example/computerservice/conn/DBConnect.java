package org.example.computerservice.conn;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {
    private static Connection connection;

    public static Connection getConn(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql:///computer_service",
                    "root",
                    "1164"
            );
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }
}
