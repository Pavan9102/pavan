package com.example.ems.dao;

import com.example.ems.db.Database;
import com.example.ems.model.Bill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDao {
    public void insert(Bill b) throws SQLException {
        String sql = "INSERT INTO bills (customer_id,meter_reading,bill_amount,paid_amount,month,year,status) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = Database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, b.getCustomerId());
            ps.setDouble(2, b.getMeterReading());
            ps.setDouble(3, b.getBillAmount());
            ps.setDouble(4, b.getPaidAmount());
            ps.setString(5, b.getMonth());
            ps.setInt(6, b.getYear());
            ps.setString(7, b.getStatus());
            ps.executeUpdate();
        }
    }

    public List<Bill> listByCustomer(int customerId, String searchMonth) throws SQLException {
        String sql = "SELECT * FROM bills WHERE customer_id=?" + (searchMonth != null && !searchMonth.isEmpty() ? " AND month LIKE ?" : "") + " ORDER BY year DESC";
        try (Connection conn = Database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            if (sql.contains("LIKE")) ps.setString(2, "%" + searchMonth + "%");
            try (ResultSet rs = ps.executeQuery()) {
                List<Bill> list = new ArrayList<>();
                while (rs.next()) list.add(mapRow(rs));
                return list;
            }
        }
    }

    public List<Bill> listUnpaidByCustomer(int customerId) throws SQLException {
        String sql = "SELECT * FROM bills WHERE customer_id=? AND status <> 'Paid'";
        try (Connection conn = Database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Bill> list = new ArrayList<>();
                while (rs.next()) list.add(mapRow(rs));
                return list;
            }
        }
    }

    public double totalConsumption(int customerId) throws SQLException {
        String sql = "SELECT COALESCE(SUM(meter_reading),0) FROM bills WHERE customer_id=?";
        try (Connection conn = Database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getDouble(1);
            }
        }
    }

    public double lastPaymentAmount(int customerId) throws SQLException {
        String sql = "SELECT bill_amount FROM bills WHERE customer_id=? AND status='Paid' ORDER BY bill_id DESC FETCH FIRST ROW ONLY";
        try (Connection conn = Database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble(1); else return 0.0;
            }
        }
    }

    public double dueAmount(int customerId) throws SQLException {
        String sql = "SELECT COALESCE(SUM(bill_amount - paid_amount),0) FROM bills WHERE customer_id=? AND status <> 'Paid'";
        try (Connection conn = Database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) { rs.next(); return rs.getDouble(1); }
        }
    }

    public void applyPayment(int billId, double amount) throws SQLException {
        String sql = "UPDATE bills SET paid_amount = paid_amount + ?, status = CASE WHEN paid_amount + ? >= bill_amount THEN 'Paid' ELSE 'Partial' END WHERE bill_id=?";
        try (Connection conn = Database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setDouble(2, amount);
            ps.setInt(3, billId);
            ps.executeUpdate();
        }
    }

    private Bill mapRow(ResultSet rs) throws SQLException {
        Bill b = new Bill();
        b.setBillId(rs.getInt("bill_id"));
        b.setCustomerId(rs.getInt("customer_id"));
        b.setMeterReading(rs.getDouble("meter_reading"));
        b.setBillAmount(rs.getDouble("bill_amount"));
        try { b.setPaidAmount(rs.getDouble("paid_amount")); } catch (SQLException ignored) { b.setPaidAmount(0); }
        b.setMonth(rs.getString("month"));
        b.setYear(rs.getInt("year"));
        b.setStatus(rs.getString("status"));
        return b;
    }
}

