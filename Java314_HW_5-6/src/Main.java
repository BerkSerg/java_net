import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/cars_db";
    private static final String USER = "root";
    private static final String PASSWORD = "1164";

    private static Connection conn;

    public static void main(String[] args) {
        try {
            initDB();

            carsList();

            closeDB();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    private static void initDB() throws SQLException {
        conn = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private static void closeDB() throws SQLException {
        conn.close();
    }

    private static void carsList() {

        try(Scanner sc = new Scanner(System.in)) {
            while(true){
                System.out.println("\nСиситема бронирования");
                System.out.println("1. Весь список автомобилей");
                System.out.println("2. Все производители");
                System.out.println("3. Автомобили по годам");
                System.out.println("4. Автомобили по производителю");
                System.out.println("5. Автомобили по цвету");
                System.out.println("6. Автомобили по объему двигателя");
                System.out.println("7. Автомобили по типу кузова");
                System.out.println("0. Выход");
                System.out.print("Выберите действие: ");

                int act = Integer.parseInt(sc.nextLine());
                switch (act){
                    case 0:
                        return;
                    case 1:
                        viewAllCars();
                        break;
                    case 2:
                        viewAllBrands();
                        break;
                    case 3:
                        viewByYear(sc);
                        break;
                    case 4:
                        viewByBrand(sc);
                        break;
                    case 5:
                        viewByColor(sc);
                        break;
                    case 6:
                        viewByVolume(sc);
                        break;
                    case 7:
                        viewByType(sc);
                        break;
                    default:
                        System.out.println("Неверный выбор. Повторите ввод.");
                }
            }
        } catch (InputMismatchException | SQLException e){
            System.err.println(e.getMessage());
        }
    }

    private static void viewAllCars() throws SQLException {
        String query = "select * from cars order by brand";
        try (Statement st = conn.createStatement()){
            ResultSet rs = st.executeQuery(query);
            printCarList(rs);
            rs.close();
        }
    }

    private static void viewByYear(Scanner sc) throws SQLException {
        System.out.println("\n-----------------------------------------------");
        System.out.println("Введите год выпуска: ");
        int year = Integer.parseInt(sc.nextLine());
        String query = "select * from cars where year=? order by brand";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, year);
            ResultSet rs = ps.executeQuery();
            printCarList(rs);
            rs.close();
        }
    }


    private static void viewAllBrands() throws SQLException {
        System.out.println("\n-----------------------------------------------");
        String query = "select distinct brand from cars order by brand";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(query)){
            while(rs.next()){
                System.out.println(rs.getString("brand"));
            }
        }
        System.out.println("-----------------------------------------------");
    }

    private static void viewByBrand(Scanner sc) throws SQLException {
        System.out.println("\n-----------------------------------------------");
        System.out.println("Введите название производителя (или часть названия): ");
        String brand = sc.nextLine();
        String query = "select * from cars where lower(brand) like ? order by brand";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, "%" + brand.toLowerCase() + "%");
            ResultSet rs = ps.executeQuery();
            printCarList(rs);
            rs.close();
        }
    }

    private static void viewByColor(Scanner sc) throws SQLException {
        System.out.println("\n-----------------------------------------------");
        System.out.println("Введите цвет (или часть названия): ");
        String brand = sc.nextLine();
        String query = "select * from cars where lower(color) like ? order by brand";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, "%" + brand.toLowerCase() + "%");
            ResultSet rs = ps.executeQuery();
            printCarList(rs);
            rs.close();
        }
    }

    private static void viewByVolume(Scanner sc) throws SQLException {
        System.out.println("\n-----------------------------------------------");
        System.out.println("Введите объем двигателя: ");
        double vol = Double.parseDouble(sc.nextLine());
        String query = "select * from cars where engine_vol = ? order by brand";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setDouble(1, vol);
            ResultSet rs = ps.executeQuery();
            printCarList(rs);
            rs.close();
        }
    }

    private static void viewByType(Scanner sc) throws SQLException {
        System.out.println("\n-----------------------------------------------");
        System.out.println("Введите тип кузова (или часть): ");
        String type = sc.nextLine();
        String query = "select * from cars where lower(color) like ? order by brand";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, "%" + type.toLowerCase() + "%");
            ResultSet rs = ps.executeQuery();
            printCarList(rs);
            rs.close();
        }
    }

    private static void printCarList(ResultSet rs) throws SQLException {
        System.out.print("\n-----------------------------------------------");
        if(rs.next()){
            do{
                System.out.printf("\n%s |%s |%s |%d |%s |%s",
                        String.format("%-15s", rs.getString("brand")),
                        String.format("%-15s", rs.getString("model")),
                        String.format("%1$,.1f", rs.getDouble("engine_vol")),
                        rs.getInt("year"),
                        String.format("%-10s", rs.getString("color")),
                        String.format("%-15s", rs.getString("car_type"))
                );
            } while(rs.next());
        }
        else{
            System.out.println("\nАвтомобили не найдены");
        }
        System.out.println("\n-----------------------------------------------");
    }
}