package conn;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnecion {
    private static Connection connection;

    public static Connection getConn(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql:///users_database",
                    "root",
                    "1164"
            );
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }
}
