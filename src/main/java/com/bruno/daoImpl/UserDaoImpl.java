package com.bruno.daoImpl;

import com.bruno.dao.UserDao;
import com.bruno.database.DbConnection;
import com.bruno.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    Connection conn = DbConnection.connect();

    @Override
    public Integer addUser(User user) {
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

        try {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.name());
            statement.setString(2, user.email());
            statement.setString(3, user.password());

            statement.execute();
            System.out.println("Usuário criado com sucesso!");

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Falha ao obter ID gerado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public void removeUserById(Integer id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);

            statement.execute();

            System.out.println("Usuário removido com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserById(Integer id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");

                return new User(id, name, email, "hashedPassword");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<User> listAllUsers() {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");

                users.add(new User(id, name, email, "hashedPassword"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
