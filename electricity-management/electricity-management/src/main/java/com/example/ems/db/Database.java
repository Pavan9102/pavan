package com.example.ems.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    // Configure these for your local Derby Network Server
    private static final String JDBC_URL = "jdbc:derby://localhost:1527/emsdb"; // create DB manually
    private static final String DB_USER = "emsuser";
    private static final String DB_PASS = "emspass";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
    }
}

