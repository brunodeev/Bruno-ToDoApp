package com.bruno.database;

import java.sql.*;

public class DbInitializer {
    public static void createDatabases() {
        String sql = """
            CREATE TABLE IF NOT EXISTS tasks (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                completed BOOLEAN NOT NULL
            );
        """;

        try (Connection conn = DbConnection.connect()){
            Statement statement = conn.createStatement();

            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
