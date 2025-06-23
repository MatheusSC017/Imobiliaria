/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.imobiliaria.dao;

import com.mycompany.imobiliaria.models.RealEstateModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Arrays;

/**
 *
 * @author user
 */
public class RealEstateDAO {
    
    private static final Set<String> VALID_COLUMNS = Set.of(
        "id", "address", "neighborhood", "number", "city", "type", 
        "rooms", "bathrooms", "area", "value", "garage"
    );
    private static final Set<String> VALID_DIRECTIONS = Set.of("ASC", "DESC");


    public void insert(RealEstateModel property) {
        String sql = """
            INSERT INTO properties(address, neighborhood, number, city, type, rooms, bathrooms, area, value, garage)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, property.getAddress());
            pstmt.setString(2, property.getNeighborhood());
            pstmt.setString(3, property.getNumber());
            pstmt.setString(4, property.getCity());
            pstmt.setString(5, property.getType());
            pstmt.setInt(6, property.getRooms());
            pstmt.setInt(7, property.getBathrooms());
            pstmt.setDouble(8, property.getArea());
            pstmt.setDouble(9, property.getValue());
            pstmt.setInt(10, property.getGarage());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error inserting real estate data", e);
        }
    }

    public void update(RealEstateModel property) {
        String sql = """
            UPDATE properties
            SET address = ?, neighborhood = ?, number = ?, city = ?, type = ?, rooms = ?, bathrooms = ?, area = ?, value = ?, garage = ?
            WHERE id = ?
        """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, property.getAddress());
            pstmt.setString(2, property.getNeighborhood());
            pstmt.setString(3, property.getNumber());
            pstmt.setString(4, property.getCity());
            pstmt.setString(5, property.getType());
            pstmt.setInt(6, property.getRooms());
            pstmt.setInt(7, property.getBathrooms());
            pstmt.setDouble(8, property.getArea());
            pstmt.setDouble(9, property.getValue());
            pstmt.setInt(10, property.getGarage());
            pstmt.setInt(11, property.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error updating real estate data", e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM properties WHERE id = ?";

        try (Connection conn = DataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error deleting real estate data", e);
        }
    }

    public List<RealEstateModel> getAll(String order_field, String order_direction) {
        String validatedOrderField = "id";
        if (order_field != null && VALID_COLUMNS.contains(order_field.toLowerCase())) {
            validatedOrderField = order_field;
        }

        String validatedOrderDirection = "ASC";
        if (order_direction != null && VALID_DIRECTIONS.contains(order_direction.toUpperCase())) {
            validatedOrderDirection = order_direction.toUpperCase();
        }

        List<RealEstateModel> properties = new ArrayList<>();
        String sql = "SELECT * FROM properties ORDER BY " + validatedOrderField + " " + validatedOrderDirection;

        try (Connection conn = DataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                RealEstateModel p = new RealEstateModel(
                    rs.getInt("id"),
                    rs.getString("address"),
                    rs.getString("neighborhood"),
                    rs.getString("number"),
                    rs.getString("city"),
                    rs.getString("type"),
                    rs.getInt("rooms"),
                    rs.getInt("bathrooms"),
                    rs.getDouble("area"),
                    rs.getDouble("value"),
                    rs.getInt("garage")
                );
                properties.add(p);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving all real estate data", e);
        }
        return properties;
    }

    
    public RealEstateModel getById(int id) {
        String sql = "SELECT * FROM properties WHERE id = ?";
        RealEstateModel property = null;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    property = new RealEstateModel(
                        rs.getInt("id"),
                        rs.getString("address"),
                        rs.getString("neighborhood"),
                        rs.getString("number"),
                        rs.getString("city"),
                        rs.getString("type"),
                        rs.getInt("rooms"),
                        rs.getInt("bathrooms"),
                        rs.getDouble("area"),
                        rs.getDouble("value"),
                        rs.getInt("garage")
                    );
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving real estate data by ID", e);
        }

        return property;
    }
}
