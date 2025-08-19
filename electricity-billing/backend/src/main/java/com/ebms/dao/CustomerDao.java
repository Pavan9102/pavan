package com.ebms.dao;

import com.ebms.config.Database;
import com.ebms.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {
    public void create(Customer customer) throws SQLException {
        String sql = "INSERT INTO APP.CUSTOMER (LOGIN_ID, NAME, EMAIL, ADDRESS, PHONE, PASSWORD_HASH) VALUES (?,?,?,?,?,?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, customer.getLoginId());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getAddress());
            ps.setString(5, customer.getPhone());
            ps.setString(6, customer.getPasswordHash());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    customer.setCustomerId(rs.getInt(1));
                }
            }
        }
    }

    public Customer findByLoginId(String loginId) throws SQLException {
        String sql = "SELECT CUSTOMER_ID, LOGIN_ID, NAME, EMAIL, ADDRESS, PHONE, PASSWORD_HASH FROM APP.CUSTOMER WHERE LOGIN_ID = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, loginId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Customer c = new Customer();
                    c.setCustomerId(rs.getInt("CUSTOMER_ID"));
                    c.setLoginId(rs.getString("LOGIN_ID"));
                    c.setName(rs.getString("NAME"));
                    c.setEmail(rs.getString("EMAIL"));
                    c.setAddress(rs.getString("ADDRESS"));
                    c.setPhone(rs.getString("PHONE"));
                    c.setPasswordHash(rs.getString("PASSWORD_HASH"));
                    return c;
                }
            }
        }
        return null;
    }

    public Customer findById(int customerId) throws SQLException {
        String sql = "SELECT CUSTOMER_ID, LOGIN_ID, NAME, EMAIL, ADDRESS, PHONE, PASSWORD_HASH FROM APP.CUSTOMER WHERE CUSTOMER_ID = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Customer c = new Customer();
                    c.setCustomerId(rs.getInt("CUSTOMER_ID"));
                    c.setLoginId(rs.getString("LOGIN_ID"));
                    c.setName(rs.getString("NAME"));
                    c.setEmail(rs.getString("EMAIL"));
                    c.setAddress(rs.getString("ADDRESS"));
                    c.setPhone(rs.getString("PHONE"));
                    c.setPasswordHash(rs.getString("PASSWORD_HASH"));
                    return c;
                }
            }
        }
        return null;
    }

    public List<Customer> findAll() throws SQLException {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT CUSTOMER_ID, LOGIN_ID, NAME, EMAIL, ADDRESS, PHONE FROM APP.CUSTOMER ORDER BY CUSTOMER_ID DESC";
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Customer c = new Customer();
                c.setCustomerId(rs.getInt("CUSTOMER_ID"));
                c.setLoginId(rs.getString("LOGIN_ID"));
                c.setName(rs.getString("NAME"));
                c.setEmail(rs.getString("EMAIL"));
                c.setAddress(rs.getString("ADDRESS"));
                c.setPhone(rs.getString("PHONE"));
                list.add(c);
            }
        }
        return list;
    }

    public void deleteIfNoDue(int customerId) throws SQLException {
        // Ensure no pending bills
        String dueCheck = "SELECT COUNT(*) FROM APP.BILL WHERE CUSTOMER_ID=? AND (STATUS='PENDING' OR STATUS='PARTIAL')";
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(dueCheck)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                if (rs.getInt(1) > 0) {
                    throw new SQLException("Cannot delete customer with pending dues");
                }
            }
        }

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM APP.CUSTOMER WHERE CUSTOMER_ID=?")) {
            ps.setInt(1, customerId);
            ps.executeUpdate();
        }
    }
}

