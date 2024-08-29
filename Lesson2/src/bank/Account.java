package bank;

import java.sql.*;
import java.util.Scanner;

public class Account {
    private Connection connection;
    private Scanner sc;
    private Long accountNumber;

    public Account(Connection connection, Scanner sc) {
        this.connection = connection;
        this.sc = sc;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public boolean accountExists(User user) throws SQLException {
        String query = "select account_number from account where email=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, user.getEmail());
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        }
    }

    public void create(User user) throws SQLException {
        String query = "insert into account values (?, ?, ?, ?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            System.out.println("Открытие счета");

            System.out.print("Введите имя:");
            String name = sc.nextLine();

            System.out.print("Введите остаток:");
            double rest = Double.parseDouble(sc.nextLine());

            System.out.print("Введите pin:");
            String pin = sc.nextLine();

            long newAccountNumber = generateAccount();

            preparedStatement.setLong(1, newAccountNumber);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setDouble(4, rest);
            preparedStatement.setString(5, pin);

            int res = preparedStatement.executeUpdate();
        }
    }

    private long generateAccount() {

        try(Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select account_number from account order by account_number desc limit 1")) {
            if (rs.next()) {
                long newAccount = rs.getLong("account_number") + 1;
                accountNumber = newAccount;
                return newAccount;
            }
        }catch (SQLException e ){
            e.printStackTrace();
        }
        return 10000100;
    }

    public void open(User user) throws SQLException {
        String query = "select account_number from account where email=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, user.getEmail());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                accountNumber = rs.getLong("account_number");
            }

        }
    }


}
