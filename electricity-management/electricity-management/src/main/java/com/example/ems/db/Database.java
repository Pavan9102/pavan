package com.example.ems.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String JDBC_URL = "jdbc:derby:/workspace/electricity-management/.derbydb;create=true";

    static {
        try {
            initSchema();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize schema", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL);
    }

    private static void initSchema() throws SQLException {
        try (Connection conn = getConnection(); Statement st = conn.createStatement()) {
            st.executeUpdate("CREATE TABLE customers (" +
                    "customer_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
                    "login_id VARCHAR(100) UNIQUE NOT NULL," +
                    "name VARCHAR(200) NOT NULL," +
                    "email VARCHAR(200) NOT NULL," +
                    "address VARCHAR(500) NOT NULL," +
                    "phone VARCHAR(50) NOT NULL," +
                    "password VARCHAR(200) NOT NULL," +
                    "role VARCHAR(20) NOT NULL DEFAULT 'CUSTOMER'" +
                    ")");
        } catch (SQLException ignored) { }

        try (Connection conn = getConnection(); Statement st = conn.createStatement()) {
            st.executeUpdate("CREATE TABLE bills (" +
                    "bill_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
                    "customer_id INT NOT NULL," +
                    "meter_reading DOUBLE NOT NULL," +
                    "bill_amount DOUBLE NOT NULL," +
                    "month VARCHAR(20) NOT NULL," +
                    "year INT NOT NULL," +
                    "status VARCHAR(20) NOT NULL," +
                    "FOREIGN KEY (customer_id) REFERENCES customers(customer_id)" +
                    ")");
        } catch (SQLException ignored) { }

        // Seed an admin user if not exists
        try (Connection conn = getConnection(); Statement st = conn.createStatement()) {
            st.executeUpdate("INSERT INTO customers (login_id,name,email,address,phone,password,role) " +
                    "SELECT 'admin','Administrator','admin@example.com','-','-','admin','ADMIN' FROM SYSIBM.SYSDUMMY1 " +
                    "WHERE NOT EXISTS (SELECT 1 FROM customers WHERE login_id='admin')");
        } catch (SQLException ignored) { }
    }
}

