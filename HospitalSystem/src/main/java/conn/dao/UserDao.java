package conn.dao;

import conn.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserDao {
    private Connection conn;

    public UserDao(Connection conn) {
        this.conn = conn;
    }

    public boolean registerUser(User user){
        boolean flag = false;
        try {
            String query = "insert into Users (full_name, email, password) values (?, ?, ?)";
            try(PreparedStatement preparedStatement = conn.prepareStatement(query)){
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setString(3, user.getPassword());
                int rows = preparedStatement.executeUpdate();
                flag = (rows>0);
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        return flag;
    }
}
