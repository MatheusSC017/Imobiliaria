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
    
    public void insert(RentalModel contract) {
        String sql = """
            INSERT INTO rental_contracts (
                contract_month, contract_year, payment_base_date, rent_value,
                landlord_name, landlord_cpf, landlord_phone, landlord_email,
                tenant_name, tenant_cpf, tenant_phone, tenant_email,
                duration_months, due_month, due_year, property_id, status
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, contract.getContractMonth());
            stmt.setInt(2, contract.getContractYear());
            stmt.setInt(3, contract.getPaymentBaseDate());
            stmt.setDouble(4, contract.getRentValue());
            stmt.setString(5, contract.getLandlordName());
            stmt.setString(6, contract.getLandlordCpf());
            stmt.setString(7, contract.getLandlordPhone());
            stmt.setString(8, contract.getLandlordEmail());
            stmt.setString(9, contract.getTenantName());
            stmt.setString(10, contract.getTenantCpf());
            stmt.setString(11, contract.getTenantPhone());
            stmt.setString(12, contract.getTenantEmail());
            stmt.setInt(13, contract.getDurationMonths());
            stmt.setInt(14, contract.getDueMonth());
            stmt.setInt(15, contract.getDueYear());
            stmt.setInt(16, contract.getPropertyId());
            stmt.setString(17, contract.getStatus());

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
                    rs.getInt("contract_month"),
                    rs.getInt("contract_year"),
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
                    rs.getInt("due_month"),
                    rs.getInt("due_year"),
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
                    rs.getInt("contract_month"),
                    rs.getInt("contract_year"),
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
                    rs.getInt("due_month"),
                    rs.getInt("due_year"),
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
                rs.getInt("contract_month"),
                rs.getInt("contract_year"),
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
                rs.getInt("due_month"),
                rs.getInt("due_year"),
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
                contract_month = ?, contract_year = ?, payment_base_date = ?, rent_value = ?,
                landlord_name = ?, landlord_cpf = ?, landlord_phone = ?, landlord_email = ?,
                tenant_name = ?, tenant_cpf = ?, tenant_phone = ?, tenant_email = ?,
                duration_months = ?, due_month = ?, due_year = ?, property_id = ?, status = ?
            WHERE id = ?
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, contract.getContractMonth());
            stmt.setInt(2, contract.getContractYear());
            stmt.setInt(3, contract.getPaymentBaseDate());
            stmt.setDouble(4, contract.getRentValue());
            stmt.setString(5, contract.getLandlordName());
            stmt.setString(6, contract.getLandlordCpf());
            stmt.setString(7, contract.getLandlordPhone());
            stmt.setString(8, contract.getLandlordEmail());
            stmt.setString(9, contract.getTenantName());
            stmt.setString(10, contract.getTenantCpf());
            stmt.setString(11, contract.getTenantPhone());
            stmt.setString(12, contract.getTenantEmail());
            stmt.setInt(13, contract.getDurationMonths());
            stmt.setInt(14, contract.getDueMonth());
            stmt.setInt(15, contract.getDueYear());
            stmt.setInt(16, contract.getPropertyId());
            stmt.setString(17, contract.getStatus());
            stmt.setInt(18, contract.getId());

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
                    rs.getInt("contract_month"),
                    rs.getInt("contract_year"),
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
                    rs.getInt("due_month"),
                    rs.getInt("due_year"),
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
