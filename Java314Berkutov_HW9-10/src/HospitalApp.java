import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class HospitalApp {

    private static final String URL = "jdbc:mysql://localhost:3306/hospital";
    private static final String USER = "root";
    private static final String PASSWORD = "1164";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Scanner sc = new Scanner(System.in)) {

            HospitalManager hospital = new HospitalManager(connection, sc);
            hospital.start();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}