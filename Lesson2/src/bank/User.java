package bank;

import java.sql.*;
import java.util.Scanner;

public class User {
    private Connection connection;
    private Scanner sc;
    private boolean isAuth = false;
    private String email;

    public User(Connection connection, Scanner sc) {
        this.connection = connection;
        this.sc = sc;
    }

    public boolean isAuth() {
        return isAuth;
    }

    public String getEmail() {
        return email;
    }

    public void register() throws SQLException {
        System.out.print("Ведите имя:");
        String name = sc.nextLine();
        System.out.print("Ведите email:");
        String email = sc.nextLine();
        System.out.print("Ведите пароль:");
        String pass1 = sc.nextLine();

        if (userExists(email)){
            System.err.println("Указанный email уже существует в базе!");
            return;
        }

        addUser(name, email, pass1);

    }

    private boolean userExists(String email) throws SQLException {
        String query = "select full_name from user where email=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        }
    }

    private void addUser(String name, String email, String password) throws SQLException {
        String query = "insert into user values (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);

            int res = preparedStatement.executeUpdate();
            if (res == 0){
                System.err.println("Что-то пошло не так!");
            } else {
                System.out.println("\n*Пользователь ууспешно зарегистрирован*\n");
            }
        }
    }


    public void logIn() throws SQLException {
        System.out.print("Ведите email:");
        String email = sc.nextLine();
        System.out.print("Ведите пароль:");
        String pass1 = sc.nextLine();

        checkAuth(email, pass1);

        if (isAuth){
            System.out.println("Вход выполнен успешно");
            this.email = email;
        } else {
            System.err.println("Неверные email и/или пароль");
        }

    }

    private void checkAuth(String email, String pass1) throws SQLException {
        String query = "select * from user where email=? and password=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, pass1);
            ResultSet rs = preparedStatement.executeQuery();
            isAuth = rs.next();
        }
    }
}
