package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class BankApp {
    private static final String URL = "jdbc:mysql://localhost:3306/bank";
    private static final String USER = "root";
    private static final String PASSWORD = "1164";

    public static void main(String[] args) {

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner sc = new Scanner(System.in)) {

            User user = new User(connection, sc);
            Account account = new Account(connection, sc);
            AccountManager accountManager = new AccountManager(connection, sc);

            while (true) {
                System.out.println("\n***Добро пожаловать в java банкинг***\nВыберите действие:");
                System.out.println("1. Регистрация");
                System.out.println("2. Авторизация");
                System.out.println("0. выход");
                System.out.print("Выш выбор: ");

                int choice = Integer.parseInt(sc.nextLine());
                switch (choice){
                    case 0:
                        System.out.println("\nДо свидания.");
                        return;
                    case 1:
                        user.register();
                        break;
                    case 2:
                        user.logIn();
                        if (user.isAuth()){
                            if(!account.accountExists(user)){
                                account.create(user);
                            }else{
                                account.open(user);
                            }
                            while (true){
                                System.out.println("\n** Главное меню **");
                                System.out.println("Текущий счет: " + account.getAccountNumber());
                                System.out.println("1. Снятие наличных");
                                System.out.println("2. Взять кредит");
                                System.out.println("3. Перевод");
                                System.out.println("4. Показать остаток");
                                System.out.println("0. Выход");
                                System.out.print("Ваш выбор:");
                                int choice2 = Integer.parseInt(sc.nextLine());
                                System.out.println();
                                switch (choice2){
                                    case 0:
                                        System.out.println("Log out");
                                        return;
                                    case 1:
                                        accountManager.debetMoney(account);
                                        break;
                                    case 2:
                                        accountManager.getLoan(account);
                                        break;
                                    case 3:
                                        accountManager.sendMoney(account);
                                        break;
                                    case 4:
                                        accountManager.checkBalance(account);
                                        break;
                                    default:
                                        System.out.println("Неверный выбор");
                                }
                            }
                        }
                        break;
                    default:
                        System.out.println("\nНеверный выбор.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
