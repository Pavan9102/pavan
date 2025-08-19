package com.ebms.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Database {
    private static Connection connection;

    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Properties properties = new Properties();
                try (InputStream in = Database.class.getClassLoader().getResourceAsStream("db.properties")) {
                    if (in == null) {
                        throw new IOException("db.properties not found in classpath");
                    }
                    properties.load(in);
                }

                String driver = properties.getProperty("db.driver");
                String url = properties.getProperty("db.url");
                String username = properties.getProperty("db.username");
                String password = properties.getProperty("db.password");

                try {
                    Class.forName(driver);
                } catch (ClassNotFoundException e) {
                    throw new SQLException("JDBC Driver not found: " + driver, e);
                }

                connection = DriverManager.getConnection(url, username, password);
                initializeSchema(connection);
            } catch (IOException e) {
                throw new SQLException("Failed to load DB configuration", e);
            }
        }
        return connection;
    }

    private static void initializeSchema(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            // Customer table
            statement.executeUpdate("CREATE TABLE APP.CUSTOMER (\n" +
                    "    CUSTOMER_ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n" +
                    "    LOGIN_ID VARCHAR(64) NOT NULL UNIQUE,\n" +
                    "    NAME VARCHAR(128) NOT NULL,\n" +
                    "    EMAIL VARCHAR(128) NOT NULL,\n" +
                    "    ADDRESS VARCHAR(256) NOT NULL,\n" +
                    "    PHONE VARCHAR(32) NOT NULL,\n" +
                    "    PASSWORD_HASH VARCHAR(256) NOT NULL\n" +
                    ")");
        } catch (SQLException ignored) { /* table may exist */ }

        try (Statement statement = connection.createStatement()) {
            // Bill table
            statement.executeUpdate("CREATE TABLE APP.BILL (\n" +
                    "    BILL_ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n" +
                    "    METER_READING DOUBLE NOT NULL,\n" +
                    "    BILL_AMOUNT DOUBLE NOT NULL,\n" +
                    "    CUSTOMER_ID INT NOT NULL REFERENCES APP.CUSTOMER(CUSTOMER_ID),\n" +
                    "    MONTH_STR VARCHAR(16) NOT NULL,\n" +
                    "    YEAR_INT INT NOT NULL,\n" +
                    "    STATUS VARCHAR(16) NOT NULL\n" +
                    ")");
        } catch (SQLException ignored) { /* table may exist */ }

        try (Statement statement = connection.createStatement()) {
            // Payment table for partial payments
            statement.executeUpdate("CREATE TABLE APP.PAYMENT (\n" +
                    "    PAYMENT_ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n" +
                    "    BILL_ID INT NOT NULL REFERENCES APP.BILL(BILL_ID),\n" +
                    "    PAID_AMOUNT DOUBLE NOT NULL,\n" +
                    "    PAID_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP\n" +
                    ")");
        } catch (SQLException ignored) { /* table may exist */ }
    }
}

