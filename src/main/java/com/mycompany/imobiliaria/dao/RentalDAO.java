/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.imobiliaria.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.imobiliaria.models.RentalModel;

/**
 *
 * @author user
 */
public class RentalDAO {
    private static final String DB_URL = "jdbc:sqlite:realestate.db";
    
    public RentalDAO() {
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS rental_contracts (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                contract_date TEXT,
                payment_base_date INTEGER,
                rent_value REAL,
                landlord_name TEXT,
                landlord_cpf TEXT,
                landlord_phone TEXT,
                landlord_email TEXT,
                tenant_name TEXT,
                tenant_cpf TEXT,
                tenant_phone TEXT,
                tenant_email TEXT,
                duration_months INTEGER,
                due_date TEXT,
                property_id INTEGER,
                status TEXT DEFAULT 'ativo',
                created_at TEXT DEFAULT CURRENT_TIMESTAMP,
                updated_at TEXT DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (property_id) REFERENCES properties(id)
            );
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(RentalModel contract) {
        String sql = """
            INSERT INTO rental_contracts (
                contract_date, payment_base_date, rent_value,
                landlord_name, landlord_cpf, landlord_phone, landlord_email,
                tenant_name, tenant_cpf, tenant_phone, tenant_email,
                duration_months, due_date, property_id, status
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, contract.getContractDate());
            stmt.setInt(2, contract.getPaymentBaseDate());
            stmt.setDouble(3, contract.getRentValue());
            stmt.setString(4, contract.getLandlordName());
            stmt.setString(5, contract.getLandlordCpf());
            stmt.setString(6, contract.getLandlordPhone());
            stmt.setString(7, contract.getLandlordEmail());
            stmt.setString(8, contract.getTenantName());
            stmt.setString(9, contract.getTenantCpf());
            stmt.setString(10, contract.getTenantPhone());
            stmt.setString(11, contract.getTenantEmail());
            stmt.setInt(12, contract.getDurationMonths());
            stmt.setString(13, contract.getDueDate());
            stmt.setInt(14, contract.getPropertyId());
            stmt.setString(15, contract.getStatus());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<RentalModel> getAll() {
        List<RentalModel> contracts = new ArrayList<>();
        String sql = "SELECT * FROM rental_contracts";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                RentalModel rental = new RentalModel(
                    rs.getInt("id"),
                    rs.getString("contract_date"),
                    rs.getInt("payment_base_date"),
                    rs.getDouble("rent_value"),
                    rs.getString("landlord_name"),
                    rs.getString("landlord_cpf"),
                    rs.getString("landlord_phone"),
                    rs.getString("landlord_email"),
                    rs.getString("tenant_name"),
                    rs.getString("tenant_cpf"),
                    rs.getString("tenant_phone"),
                    rs.getString("tenant_email"),
                    rs.getInt("duration_months"),
                    rs.getString("due_date"),
                    rs.getInt("property_id"),
                    rs.getString("status")
                );
                contracts.add(rental);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contracts;
    }
    
    public List<RentalModel> getAllByPropertyId(int propertyId) {
        List<RentalModel> contracts = new ArrayList<>();
        String sql = "SELECT * FROM rental_contracts WHERE property_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, propertyId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RentalModel rental = new RentalModel(
                    rs.getInt("id"),
                    rs.getString("contract_date"),
                    rs.getInt("payment_base_date"),
                    rs.getDouble("rent_value"),
                    rs.getString("landlord_name"),
                    rs.getString("landlord_cpf"),
                    rs.getString("landlord_phone"),
                    rs.getString("landlord_email"),
                    rs.getString("tenant_name"),
                    rs.getString("tenant_cpf"),
                    rs.getString("tenant_phone"),
                    rs.getString("tenant_email"),
                    rs.getInt("duration_months"),
                    rs.getString("due_date"),
                    rs.getInt("property_id"),
                    rs.getString("status")
                );
                contracts.add(rental);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contracts;
    }
    
    public RentalModel getLastActiveContract(int propertyId) {
        RentalModel rental = null;
        String sql = "SELECT * FROM rental_contracts WHERE property_id = ? AND status = ? ORDER BY id DESC";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, propertyId);
            stmt.setString(2, "Ativo");
            ResultSet rs = stmt.executeQuery();

            rs.next();
            rental = new RentalModel(
                rs.getInt("id"),
                rs.getString("contract_date"),
                rs.getInt("payment_base_date"),
                rs.getDouble("rent_value"),
                rs.getString("landlord_name"),
                rs.getString("landlord_cpf"),
                rs.getString("landlord_phone"),
                rs.getString("landlord_email"),
                rs.getString("tenant_name"),
                rs.getString("tenant_cpf"),
                rs.getString("tenant_phone"),
                rs.getString("tenant_email"),
                rs.getInt("duration_months"),
                rs.getString("due_date"),
                rs.getInt("property_id"),
                rs.getString("status")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rental;
    }
    
    public void update(RentalModel contract) {
        String sql = """
            UPDATE rental_contracts SET
                contract_date = ?, payment_base_date = ?, rent_value = ?,
                landlord_name = ?, landlord_cpf = ?, landlord_phone = ?, landlord_email = ?,
                tenant_name = ?, tenant_cpf = ?, tenant_phone = ?, tenant_email = ?,
                duration_months = ?, due_date = ?, property_id = ?, status = ?,
                updated_at = CURRENT_TIMESTAMP
            WHERE id = ?
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, contract.getContractDate());
            stmt.setInt(2, contract.getPaymentBaseDate());
            stmt.setDouble(3, contract.getRentValue());
            stmt.setString(4, contract.getLandlordName());
            stmt.setString(5, contract.getLandlordCpf());
            stmt.setString(6, contract.getLandlordPhone());
            stmt.setString(7, contract.getLandlordEmail());
            stmt.setString(8, contract.getTenantName());
            stmt.setString(9, contract.getTenantCpf());
            stmt.setString(10, contract.getTenantPhone());
            stmt.setString(11, contract.getTenantEmail());
            stmt.setInt(12, contract.getDurationMonths());
            stmt.setString(13, contract.getDueDate());
            stmt.setInt(14, contract.getPropertyId());
            stmt.setString(15, contract.getStatus());
            stmt.setInt(16, contract.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public RentalModel getById(int id) {
        String sql = "SELECT * FROM rental_contracts WHERE id = ?";
        RentalModel rental = null;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                rental = new RentalModel(
                    rs.getInt("id"),
                    rs.getString("contract_date"),
                    rs.getInt("payment_base_date"),
                    rs.getDouble("rent_value"),
                    rs.getString("landlord_name"),
                    rs.getString("landlord_cpf"),
                    rs.getString("landlord_phone"),
                    rs.getString("landlord_email"),
                    rs.getString("tenant_name"),
                    rs.getString("tenant_cpf"),
                    rs.getString("tenant_phone"),
                    rs.getString("tenant_email"),
                    rs.getInt("duration_months"),
                    rs.getString("due_date"),
                    rs.getInt("property_id"),
                    rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rental;
    }
}
