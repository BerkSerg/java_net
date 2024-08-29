package bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {
    private Connection connection;
    private Scanner sc;

    public AccountManager(Connection connection, Scanner sc) {
        this.connection = connection;
        this.sc = sc;
    }

    private boolean updateBalance(Long account_number, double amount, boolean isCredit) throws SQLException {
        String debQuery;
        if (isCredit){
            debQuery = "update account set balance = balance + ? where account_number = ?";
        } else {
            debQuery = "update account set balance = balance - ? where account_number = ?";
        }
        try (PreparedStatement debStatement = connection.prepareStatement(debQuery)) {
            debStatement.setDouble(1, amount);
            debStatement.setLong(2, account_number);
            int res = debStatement.executeUpdate();
            return res > 0;
        }
    }

    private Double getBalance(long account_number, String pin) throws SQLException {
        String query = "select * from account where account_number=? and pin=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setLong(1, account_number);
            preparedStatement.setString(2, pin);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        }
        return null;
    }

    private boolean accountExists(long account_number) throws SQLException {
        String query = "select * from account where account_number=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setLong(1, account_number);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        }
    }

    public void debetMoney(Account account) throws SQLException {

        System.out.print("Введите pin:");
        String pin = sc.nextLine();
        long currentAccount = account.getAccountNumber();

        try {
            connection.setAutoCommit(false);
            Double balance = getBalance(currentAccount, pin);
            if (balance != null) {
                System.out.print("Введите сумму:");
                double amount  = Double.parseDouble(sc.nextLine());
                System.out.println();

                if (amount <= balance){
                    if (updateBalance(currentAccount, amount, false)) {
                        System.out.println("Списано успешно " + amount + "; баланс = " + (balance - amount));
                        connection.commit();
                    } else {
                        System.err.println("Ошибка обновления account. Списание не удалось");
                        connection.rollback();
                    }
                } else {
                    System.out.println("Недостаточный баланс");
                }
            } else {
                System.out.println("Не верный pin");
            }
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void getLoan(Account account) throws SQLException {
        System.out.print("Введите pin:");
        String pin = sc.nextLine();
        long currentAccount = account.getAccountNumber();

        try {
            connection.setAutoCommit(false);
            Double balance = getBalance(currentAccount, pin);
            if (balance != null) {
                    System.out.print("Введите сумму займа:");
                    double amount  = Double.parseDouble(sc.nextLine());
                    System.out.println();
                    if (updateBalance(currentAccount, amount, true)) {
                        System.out.println("Зачислено успешно " + amount + "; баланс = " + (balance + amount));
                        connection.commit();
                    } else {
                        System.err.println("Ошибка обновления account. Зачисление не удалось");
                        connection.rollback();
                    }
                } else {
                    System.out.println("Не верный pin");
                }
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void sendMoney(Account account) throws SQLException {
        System.out.print("Введите pin:");
        String pin = sc.nextLine();
        long senderAccount = account.getAccountNumber();
        try {
            connection.setAutoCommit(false);
            Double balance = getBalance(senderAccount, pin);
            if (balance != null) {

                System.out.print("Введите счет получателя:");
                long receiverAccount = Long.parseLong(sc.nextLine());
                System.out.println();
                if (accountExists(receiverAccount)) {
                    System.out.print("Введите сумму:");
                    double amount = Double.parseDouble(sc.nextLine());
                    System.out.println();

                    if (amount <= balance){
                        if (updateBalance(senderAccount, amount, false)) {
                            if (updateBalance(receiverAccount, amount, true)) {
                                System.out.println("Перевод выполнен успешно " + amount + "; баланс = " + (balance - amount));
                                connection.commit();
                            }
                        } else {
                            System.err.println("Ошибка обновления account. Перевод не выполнен");
                            connection.rollback();
                        }
                    } else {
                        System.out.println("Недостаточный баланс");
                    }
                } else {
                    System.err.println("Счет получателя не найден");
                }
            } else {
                System.err.println("Не верный pin");
            }

        } finally {
            connection.setAutoCommit(true);
        }
    }


    public void checkBalance(Account account) throws SQLException {
        System.out.print("Введите pin:");
        String pin = sc.nextLine();
        long currentAccount = account.getAccountNumber();
        Double balance = getBalance(currentAccount, pin);
        if (balance != null) {
            System.out.println("Текущий баланс = " + balance);
        } else {
            System.out.println("Не верный pin");
        }

    }
}
