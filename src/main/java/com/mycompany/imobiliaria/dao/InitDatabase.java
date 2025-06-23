/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.imobiliaria.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author user
 */
public class InitDatabase {

    public InitDatabase() {
        createRealEstateTableIfNotExists();
        createRentalTableIfNotExists();
        createPaymentTableIfNotExists();
    }
    
    private void createRealEstateTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS properties (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                address TEXT,
                neighborhood TEXT,
                number TEXT,
                city TEXT,
                type TEXT,
                rooms INTEGER,
                bathrooms INTEGER,
                area REAL,
                value REAL,
                garage TEXT
            );
        """;

        try (Connection conn = DataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new DataAccessException("Error initializing table: properties", e);
        }
    }

    
    private void createRentalTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS rental_contracts (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                contract_month INTEGER,
                contract_year INTEGER,
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
                due_month INTEGER,
                due_year INTEGER,
                property_id INTEGER,
                status TEXT DEFAULT 'ativo',
                contract TEXT,
                FOREIGN KEY (property_id) REFERENCES properties(id)
            );
        """;

        try (Connection conn = DataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new DataAccessException("Error initializing table: rental_contracts", e);
        }
    }
    
    private void createPaymentTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS payments (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                payment_day INTEGER,
                payment_month INTEGER,
                payment_year INTEGER,
                reference_month INTEGER,
                reference_year INTEGER,
                receipt TEXT,
                rental_id INTEGER,
                FOREIGN KEY (rental_id) REFERENCES rental_contracts(id)
            );
        """;

        try (Connection conn = DataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new DataAccessException("Error initializing table: payments", e);
        }
    }
    
}
