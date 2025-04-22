/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.imobiliaria.controllers;

import com.mycompany.imobiliaria.dao.PaymentDAO;
import com.mycompany.imobiliaria.models.PaymentModel;
import java.util.List;

/**
 *
 * @author user
 */
public class PaymentController {
    private PaymentDAO dao;

    public PaymentController() {
        dao = new PaymentDAO();
    }

    public void addPayment(PaymentModel payment) {
        dao.insert(payment);
    }

    public List<PaymentModel> getAllPayments(int rentalId) {
        return dao.getAll(rentalId);
    }

}
