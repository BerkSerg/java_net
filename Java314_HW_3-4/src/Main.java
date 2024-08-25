import java.sql.*;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/cars_db";
    private static final String USER = "root";
    private static final String PASSWORD = "1164";

    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            String query = "create table cars (" +
                    "id int auto_increment primary key, " +
                    "brand varchar(255), " +
                    "model varchar(255), " +
                    "engine_vol numeric(2,1), " +
                    "year int, " +
                    "color varchar(50), " +
                    "car_type varchar(50)" +
                    ")";
            Statement st = conn.createStatement();
            st.executeUpdate(query);

            query = "INSERT INTO cars (brand, model, engine_vol, year, color, car_type) VALUES\n" +
                    "('Toyota', 'Camry', 2.5, 2020, 'Черный', 'Седан'),\n" +
                    "('Toyota', 'Corolla', 1.8, 2019, 'Белый', 'Седан'),\n" +
                    "('Toyota', 'RAV4', 2.0, 2021, 'Синий', 'Кроссовер'),\n" +
                    "('Honda', 'Civic', 1.8, 2018, 'Серый', 'Седан'),\n" +
                    "('Honda', 'Accord', 2.4, 2020, 'Красный', 'Седан'),\n" +
                    "('Honda', 'CR-V', 2.0, 2019, 'Белый', 'Кроссовер'),\n" +
                    "('Ford', 'Focus', 2.0, 2018, 'Черный', 'Хэтчбек'),\n" +
                    "('Ford', 'Fiesta', 1.6, 2019, 'Серый', 'Хэтчбек'),\n" +
                    "('Ford', 'Escape', 2.5, 2020, 'Белый', 'Кроссовер'),\n" +
                    "('BMW', 'X5', 3.0, 2021, 'Синий', 'Внедорожник'),\n" +
                    "('BMW', '3 Series', 2.0, 2019, 'Черный', 'Седан'),\n" +
                    "('BMW', 'X3', 2.0, 2020, 'Белый', 'Кроссовер'),\n" +
                    "('Audi', 'A4', 2.0, 2019, 'Красный', 'Седан'),\n" +
                    "('Audi', 'Q5', 2.0, 2020, 'Серый', 'Кроссовер'),\n" +
                    "('Audi', 'A6', 2.5, 2021, 'Черный', 'Седан'),\n" +
                    "('Mercedes-Benz', 'C-Class', 2.0, 2020, 'Серебристый', 'Седан'),\n" +
                    "('Mercedes-Benz', 'GLC', 2.2, 2021, 'Белый', 'Кроссовер'),\n" +
                    "('Mercedes-Benz', 'E-Class', 2.5, 2019, 'Серый', 'Седан'),\n" +
                    "('Nissan', 'Qashqai', 1.6, 2018, 'Белый', 'Кроссовер'),\n" +
                    "('Nissan', 'X-Trail', 2.0, 2020, 'Синий', 'Кроссовер')";

            st.executeUpdate(query);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}