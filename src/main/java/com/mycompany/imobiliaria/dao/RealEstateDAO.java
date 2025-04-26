/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.imobiliaria.dao;

import com.mycompany.imobiliaria.models.RealEstateModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class RealEstateDAO {
    private static final String DB_URL = "jdbc:sqlite:realestate.db";

    public void insert(RealEstateModel property) {
        String sql = """
            INSERT INTO properties(address, neighborhood, number, city, type, rooms, bathrooms, area, value, garage)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
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
            e.printStackTrace();
        }
    }

    public List<RealEstateModel> getAll() {
        List<RealEstateModel> properties = new ArrayList<>();
        String sql = "SELECT * FROM properties";

        try (Connection conn = DriverManager.getConnection(DB_URL);
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
            e.printStackTrace();
        }

        return properties;
    }

    public void update(RealEstateModel property) {
        String sql = """
            UPDATE properties
            SET address = ?, neighborhood = ?, number = ?, city = ?, type = ?, rooms = ?, bathrooms = ?, area = ?, value = ?, garage = ?
            WHERE id = ?
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
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
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM properties WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public RealEstateModel getById(int id) {
        String sql = "SELECT * FROM properties WHERE id = ?";
        RealEstateModel property = null;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return property;
    }
}
