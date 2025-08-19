package com.ebms.dao;

import com.ebms.config.Database;
import com.ebms.model.Bill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDao {
    public void create(Bill bill) throws SQLException {
        String sql = "INSERT INTO APP.BILL (METER_READING, BILL_AMOUNT, CUSTOMER_ID, MONTH_STR, YEAR_INT, STATUS) VALUES (?,?,?,?,?,?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDouble(1, bill.getMeterReading());
            ps.setDouble(2, bill.getBillAmount());
            ps.setInt(3, bill.getCustomerId());
            ps.setString(4, bill.getMonthStr());
            ps.setInt(5, bill.getYearInt());
            ps.setString(6, bill.getStatus());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) bill.setBillId(rs.getInt(1));
            }
        }
    }

    public List<Bill> listByCustomer(int customerId, String query) throws SQLException {
        List<Bill> list = new ArrayList<>();
        String base = "SELECT BILL_ID, METER_READING, BILL_AMOUNT, CUSTOMER_ID, MONTH_STR, YEAR_INT, STATUS FROM APP.BILL WHERE CUSTOMER_ID=?";
        String sql = base + (query != null && !query.isEmpty() ? " AND (LOWER(MONTH_STR) LIKE ? OR CAST(YEAR_INT AS VARCHAR(4)) LIKE ?)" : "") +
                " ORDER BY YEAR_INT DESC, BILL_ID DESC";
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            if (query != null && !query.isEmpty()) {
                String like = "%" + query.toLowerCase() + "%";
                ps.setString(2, like);
                ps.setString(3, like);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Bill b = map(rs);
                    list.add(b);
                }
            }
        }
        return list;
    }

    public Bill getById(int billId) throws SQLException {
        String sql = "SELECT BILL_ID, METER_READING, BILL_AMOUNT, CUSTOMER_ID, MONTH_STR, YEAR_INT, STATUS FROM APP.BILL WHERE BILL_ID=?";
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, billId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    public double getTotalPaidForBill(int billId) throws SQLException {
        String sql = "SELECT COALESCE(SUM(PAID_AMOUNT),0) FROM APP.PAYMENT WHERE BILL_ID=?";
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, billId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getDouble(1);
            }
        }
    }

    public void updateStatus(int billId, String status) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE APP.BILL SET STATUS=? WHERE BILL_ID=?")) {
            ps.setString(1, status);
            ps.setInt(2, billId);
            ps.executeUpdate();
        }
    }

    private Bill map(ResultSet rs) throws SQLException {
        Bill b = new Bill();
        b.setBillId(rs.getInt("BILL_ID"));
        b.setMeterReading(rs.getDouble("METER_READING"));
        b.setBillAmount(rs.getDouble("BILL_AMOUNT"));
        b.setCustomerId(rs.getInt("CUSTOMER_ID"));
        b.setMonthStr(rs.getString("MONTH_STR"));
        b.setYearInt(rs.getInt("YEAR_INT"));
        b.setStatus(rs.getString("STATUS"));
        return b;
    }
}

