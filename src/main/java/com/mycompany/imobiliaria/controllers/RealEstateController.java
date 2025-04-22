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

    public List<RealEstateModel> getAllProperties() {
        return dao.getAll();
    }

    public RealEstateModel getPropertyById(int id) {
        return dao.getById(id);
    }
}
