/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.imobiliaria.models;

/**
 *
 * @author user
 */
public class RentalModel {
    private int id;
    private String contractDate;
    private int paymentBaseDate;
    private double rentValue;
    private String landlordName;
    private String landlordCpf;
    private String landlordPhone;
    private String landlordEmail;
    private String tenantName;
    private String tenantCpf;
    private String tenantPhone;
    private String tenantEmail;
    private int durationMonths;
    private String dueDate;
    private int propertyId;
    private String status;

    public RentalModel(String contractDate, int paymentBaseDate, double rentValue,
                            String landlordName, String landlordCpf, String landlordPhone, String landlordEmail,
                            String tenantName, String tenantCpf, String tenantPhone, String tenantEmail,
                            int durationMonths, String dueDate, int propertyId, String status) {
        setContractDate(contractDate);
        setPaymentBaseDate(paymentBaseDate);
        setRentValue(rentValue);
        setLandlordName(landlordName);
        setLandlordCpf(landlordCpf);
        setLandlordPhone(landlordPhone);
        setLandlordEmail(landlordEmail);
        setTenantName(tenantName);
        setTenantCpf(tenantCpf);
        setTenantPhone(tenantPhone);
        setTenantEmail(tenantEmail);
        setDurationMonths(durationMonths);
        setDueDate(dueDate);
        setPropertyId(propertyId);
        setStatus(status);
    }
    
    public RentalModel(int id, String contractDate, int paymentBaseDate, double rentValue,
                            String landlordName, String landlordCpf, String landlordPhone, String landlordEmail,
                            String tenantName, String tenantCpf, String tenantPhone, String tenantEmail,
                            int durationMonths, String dueDate, int propertyId, String status) {
        setId(id);
        setContractDate(contractDate);
        setPaymentBaseDate(paymentBaseDate);
        setRentValue(rentValue);
        setLandlordName(landlordName);
        setLandlordCpf(landlordCpf);
        setLandlordPhone(landlordPhone);
        setLandlordEmail(landlordEmail);
        setTenantName(tenantName);
        setTenantCpf(tenantCpf);
        setTenantPhone(tenantPhone);
        setTenantEmail(tenantEmail);
        setDurationMonths(durationMonths);
        setDueDate(dueDate);
        setPropertyId(propertyId);
        setStatus(status);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }

    public void setPaymentBaseDate(int paymentBaseDate) {
        this.paymentBaseDate = paymentBaseDate;
    }

    public void setRentValue(double rentValue) {
        this.rentValue = rentValue;
    }

    public void setLandlordName(String landlordName) {
        this.landlordName = landlordName;
    }

    public void setLandlordCpf(String landlordCpf) {
        this.landlordCpf = landlordCpf;
    }

    public void setLandlordPhone(String landlordPhone) {
        this.landlordPhone = landlordPhone;
    }

    public void setLandlordEmail(String landlordEmail) {
        this.landlordEmail = landlordEmail;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public void setTenantCpf(String tenantCpf) {
        this.tenantCpf = tenantCpf;
    }

    public void setTenantPhone(String tenantPhone) {
        this.tenantPhone = tenantPhone;
    }

    public void setTenantEmail(String tenantEmail) {
        this.tenantEmail = tenantEmail;
    }

    public void setDurationMonths(int durationMonths) {
        this.durationMonths = durationMonths;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public int getId() {
        return id;
    }

    public String getContractDate() {
        return contractDate;
    }

    public int getPaymentBaseDate() {
        return paymentBaseDate;
    }

    public double getRentValue() {
        return rentValue;
    }

    public String getLandlordName() {
        return landlordName;
    }

    public String getLandlordCpf() {
        return landlordCpf;
    }

    public String getLandlordPhone() {
        return landlordPhone;
    }

    public String getLandlordEmail() {
        return landlordEmail;
    }

    public String getTenantName() {
        return tenantName;
    }

    public String getTenantCpf() {
        return tenantCpf;
    }

    public String getTenantPhone() {
        return tenantPhone;
    }

    public String getTenantEmail() {
        return tenantEmail;
    }

    public int getDurationMonths() {
        return durationMonths;
    }

    public String getDueDate() {
        return dueDate;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public String getStatus() {
        return status;
    }

}
