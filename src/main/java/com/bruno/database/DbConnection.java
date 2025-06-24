package com.bruno.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    public static Connection connect() {
        Connection connection;
        String dbUrl = "jdbc:h2:mem:todo;DB_CLOSE_DELAY=-1";
        String dbUser = "sa";
        String dbPassword = "";

        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
