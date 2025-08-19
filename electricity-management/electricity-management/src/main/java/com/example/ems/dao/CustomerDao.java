package com.example.ems.dao;

import com.example.ems.db.Database;
import com.example.ems.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {
    public void insert(Customer c) throws SQLException {
        String sql = "INSERT INTO customers (login_id,name,email,address,phone,password,role) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = Database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getLoginId());
            ps.setString(2, c.getName());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getAddress());
            ps.setString(5, c.getPhone());
            ps.setString(6, c.getPassword());
            ps.setString(7, c.getRole() == null ? "CUSTOMER" : c.getRole());
            ps.executeUpdate();
        }
    }

    public Customer findByLoginAndPassword(String loginId, String password) throws SQLException {
        String sql = "SELECT * FROM customers WHERE login_id=? AND password=?";
        try (Connection conn = Database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, loginId);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    public Customer findById(int customerId) throws SQLException {
        String sql = "SELECT * FROM customers WHERE customer_id=?";
        try (Connection conn = Database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    public List<Customer> findAll() throws SQLException {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customers ORDER BY customer_id";
        try (Connection conn = Database.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    public boolean deleteIfNoDue(int customerId) throws SQLException {
        // Ensure no due bills
        String dueSql = "SELECT COUNT(*) FROM bills WHERE customer_id=? AND status <> 'Paid'";
        try (Connection conn = Database.getConnection(); PreparedStatement ps = conn.prepareStatement(dueSql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                if (rs.getInt(1) > 0) return false;
            }
        }
        try (Connection conn = Database.getConnection(); PreparedStatement ps = conn.prepareStatement("DELETE FROM customers WHERE customer_id=?")) {
            ps.setInt(1, customerId);
            ps.executeUpdate();
            return true;
        }
    }

    private Customer mapRow(ResultSet rs) throws SQLException {
        Customer c = new Customer();
        c.setCustomerId(rs.getInt("customer_id"));
        c.setLoginId(rs.getString("login_id"));
        c.setName(rs.getString("name"));
        c.setEmail(rs.getString("email"));
        c.setAddress(rs.getString("address"));
        c.setPhone(rs.getString("phone"));
        c.setPassword(rs.getString("password"));
        c.setRole(rs.getString("role"));
        return c;
    }
}

