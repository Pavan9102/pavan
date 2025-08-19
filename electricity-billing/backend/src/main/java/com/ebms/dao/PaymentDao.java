package com.ebms.dao;

import com.ebms.config.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentDao {
    public void create(int billId, double amount) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO APP.PAYMENT (BILL_ID, PAID_AMOUNT) VALUES (?,?)")) {
            ps.setInt(1, billId);
            ps.setDouble(2, amount);
            ps.executeUpdate();
        }
    }
}

