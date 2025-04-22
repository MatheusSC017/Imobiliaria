/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.imobiliaria.controllers;

import com.mycompany.imobiliaria.dao.RentalDAO;
import com.mycompany.imobiliaria.models.RentalModel;
import java.util.List;

/**
 *
 * @author user
 */
public class RentalController {
    private RentalDAO dao;

    public RentalController() {
        dao = new RentalDAO();
    }

    public void addRental(RentalModel rental) {
        dao.insert(rental);
    }

    public void updateRental(RentalModel rental) {
        dao.update(rental);
    }

    public List<RentalModel> getAllRentals() {
        return dao.getAll();
    }

    public RentalModel getRentalById(int id) {
        return dao.getById(id);
    }
}
