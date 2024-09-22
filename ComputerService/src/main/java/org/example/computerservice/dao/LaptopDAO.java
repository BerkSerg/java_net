package org.example.computerservice.dao;

import org.example.computerservice.entity.LaptopFilter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LaptopDAO {
    private final Connection connection;

    public LaptopDAO(Connection connection) {
        this.connection = connection;
    }

    public List<String> getLaptops(LaptopFilter filter) {
        ArrayList<String> laptopList = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM Laptops" + filter.getSQLFilter();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                laptopList.add(String.format(
                        "Производитель: %s, Процессор: %s, Экран: %s, Разрешение: %s, ОС: %s",
                        rs.getString("manufacture"),
                        rs.getString("cpu"),
                        rs.getString("matrix_type"),
                        rs.getString("resolution"),
                        rs.getString("os")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return laptopList;
    }
}
