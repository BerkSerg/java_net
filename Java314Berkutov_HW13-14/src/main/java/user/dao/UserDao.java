package user.dao;

import user.entity.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDao {
    private Connection conn;

    public UserDao(Connection conn) {
        this.conn = conn;
    }

    public boolean registerUser(User user) throws SQLException {
        boolean result = false;
        String regQuery = "insert into users (login, user_name, password) values (?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(regQuery)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPassword());
            int rows = preparedStatement.executeUpdate();
            result = rows > 0;
        }
        return result;
    }

    public ArrayList<User> getAllUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        String query = "select * from users";
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                User u = new User(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
                u.setId(resultSet.getInt(1));
                users.add(u);

            }

        }
        return users;
    }

    public boolean deleteUserById(int id) throws SQLException {
        boolean result;
        String regQuery = "delete from users where id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(regQuery)) {
            preparedStatement.setInt(1, id);
            int rows = preparedStatement.executeUpdate();
            result = rows > 0;
        }
        return result;
    }
}
