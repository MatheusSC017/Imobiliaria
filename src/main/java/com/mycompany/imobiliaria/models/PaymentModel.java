/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.imobiliaria.models;

/**
 *
 * @author user
 */
public class PaymentModel {
    private int id;
    private int payment_day;
    private int payment_month;
    private int payment_year;
    private int reference_month;
    private int reference_year;
    private int rentalId;
    private String contract;

    public PaymentModel(int id, int payment_day, int payment_month, int payment_year, int reference_month, int reference_year, int rentalId, String contract) {
        setId(id);
        setPayment_day(payment_day);
        setPayment_month(payment_month);
        setPayment_year(payment_year);
        setReference_month(reference_month);
        setReference_year(reference_year);
        setRentalId(rentalId);
        setContract(contract);
    }
    
    public PaymentModel(int payment_day, int payment_month, int payment_year, int reference_month, int reference_year, int rentalId, String contract) {
        setPayment_day(payment_day);
        setPayment_month(payment_month);
        setPayment_year(payment_year);
        setReference_month(reference_month);
        setReference_year(reference_year);
        setRentalId(rentalId);
        setContract(contract);
    }

    public int getId() {
        return id;
    }

    public int getPayment_day() {
        return payment_day;
    }

    public int getPayment_month() {
        return payment_month;
    }

    public int getPayment_year() {
        return payment_year;
    }

    public int getReference_month() {
        return reference_month;
    }

    public int getReference_year() {
        return reference_year;
    }

    public int getRentalId() {
        return rentalId;
    }

    public String getContract() {
        return contract;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPayment_day(int payment_day) {
        this.payment_day = payment_day;
    }

    public void setPayment_month(int payment_month) {
        this.payment_month = payment_month;
    }

    public void setPayment_year(int payment_year) {
        this.payment_year = payment_year;
    }

    public void setReference_month(int reference_month) {
        this.reference_month = reference_month;
    }

    public void setReference_year(int reference_year) {
        this.reference_year = reference_year;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

}
