/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.imobiliaria.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.imobiliaria.models.PaymentModel;

/**
 *
 * @author user
 */
public class PaymentDAO {
    private static final String DB_URL = "jdbc:sqlite:realestate.db";

    public void insert(PaymentModel payment) {
        String sql = "INSERT INTO payments (payment_day, payment_month, payment_year, reference_month, reference_year, rental_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, payment.getPayment_day());
            stmt.setInt(2, payment.getPayment_month());
            stmt.setInt(3, payment.getPayment_year());
            stmt.setInt(4, payment.getReference_month());
            stmt.setInt(5, payment.getReference_year());
            stmt.setInt(6, payment.getRentalId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addReceipt(int id, String receipt) {
        String sql = """
            UPDATE payments SET receipt = ? WHERE id = ?
        """;
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, receipt);
            stmt.setInt(2, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PaymentModel> getAll(int rentalId) {
        String sql = "SELECT * FROM payments WHERE rental_id = ? ORDER BY reference_year DESC, reference_month DESC";
        List<PaymentModel> payments = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, rentalId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                payments.add(mapResultSetToPayment(rs));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return payments;
    }
    
    public PaymentModel get(int id) {
        String sql = "SELECT * FROM payments WHERE id = ?";
        PaymentModel payment = null;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                payment = mapResultSetToPayment(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return payment;
    }

    private PaymentModel mapResultSetToPayment(ResultSet rs) throws SQLException {
        PaymentModel payment = new PaymentModel(
            rs.getInt("id"),
            rs.getInt("payment_day"),
            rs.getInt("payment_month"),
            rs.getInt("payment_year"),
            rs.getInt("reference_month"),
            rs.getInt("reference_year"),
            rs.getInt("rental_id"),
            rs.getString("receipt")
        );
        return payment;
    }
    
}
