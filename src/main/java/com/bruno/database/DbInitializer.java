package com.bruno.database;

import java.sql.*;

public class DbInitializer {
    public static void createDatabases() {
        String dbUrl = "jdbc:h2:mem:todo";
        String dbUser = "sa";
        String dbPassword = "";

        String sql = """
            CREATE TABLE IF NOT EXISTS tasks (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                completed BOOLEAN NOT NULL
            );
        
            CREATE TABLE IF NOT EXISTS users (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                email VARCHAR(255) NOT NULL UNIQUE,
                password VARCHAR(255) NOT NULL
            );
        """;

        try {
            Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            Statement statement = conn.createStatement();

            statement.execute(sql);
            System.out.println("Tabelas criadas com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
