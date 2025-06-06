package com.bruno.dao;

import com.bruno.database.DbConnection;
import com.bruno.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDaoJdbc implements TaskDao {
    Connection conn = DbConnection.connect();

    @Override
    public Integer addTask(Task task) {
        String sql = "INSERT INTO tasks (name, completed) VALUES (?, ?)";

        try {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, task.name());
            statement.setBoolean(2, task.completed());

            statement.execute();

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
    public void removeTaskById(Integer id) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Task getTaskById(Integer id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";

        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                boolean completed = rs.getBoolean("completed");

                return new Task(id, name, completed);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Task> listAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                boolean completed = rs.getBoolean("completed");

                tasks.add(new Task(id, name, completed));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    @Override
    public boolean updateTask(Task task) {
        String sql = "UPDATE tasks SET name = ?, completed = ? WHERE id = ?";

        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, task.name());
            statement.setBoolean(2, task.completed());
            statement.setInt(3, task.id());

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
