/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.imobiliaria.controllers;

import com.mycompany.imobiliaria.models.RealEstateModel;
import com.mycompany.imobiliaria.dao.RealEstateDAO;

import java.util.List;

/**
 *
 * @author user
 */
public class RealEstateController {
    private RealEstateDAO dao;

    public RealEstateController() {
        dao = new RealEstateDAO();
    }

    public void addProperty(RealEstateModel property) {
        dao.insert(property);
    }

    public void updateProperty(RealEstateModel property) {
        dao.update(property);
    }

    public void deleteProperty(int id) {
        dao.delete(id);
    }

    public List<RealEstateModel> getAllProperties(int order_field, int order_direction) {
        String[] fields = {"id", "address", "neighborhood", "number", "city", "type", "rooms", "bathrooms", "area", "value", "garage"};
        String[] directions = {"ASC", "DESC"};
        
        if (order_field < 0 || order_field >= fields.length) order_field = 0;
        if (order_direction < 0 || order_direction >= directions.length) order_direction = 0;
        
        return dao.getAll(fields[order_field], directions[order_direction]);
    }

    public RealEstateModel getPropertyById(int id) {
        return dao.getById(id);
    }
}
