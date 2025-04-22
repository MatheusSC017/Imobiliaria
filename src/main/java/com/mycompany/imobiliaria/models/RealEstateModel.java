/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.imobiliaria.models;

/**
 *
 * @author user
 */
public class RealEstateModel {
    private int id;
    private String address;
    private String neighborhood;
    private String number;
    private String city;
    private String type;
    private int rooms;
    private int bathrooms;
    private double area;
    private double value;
    private int garage;

    public RealEstateModel(String address, String neighborhood, String number, String city,
                    String type, int rooms, int bathrooms, double area, double value, int garage) {
        setAddress(address);
        setNeighborhood(neighborhood);
        setNumber(number);
        setCity(city);
        setType(type);
        setRooms(rooms);
        setBathrooms(bathrooms);
        setArea(area);
        setValue(value);
        setGarage(garage);
    }
    
    public RealEstateModel(int id, String address, String neighborhood, String number, String city,
                    String type, int rooms, int bathrooms, double area, double value, int garage) {
        setId(id);
        setAddress(address);
        setNeighborhood(neighborhood);
        setNumber(number);
        setCity(city);
        setType(type);
        setRooms(rooms);
        setBathrooms(bathrooms);
        setArea(area);
        setValue(value);
        setGarage(garage);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setGarage(int garage) {
        this.garage = garage;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getNumber() {
        return number;
    }

    public String getCity() {
        return city;
    }

    public String getType() {
        return type;
    }

    public int getRooms() {
        return rooms;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public double getArea() {
        return area;
    }

    public double getValue() {
        return value;
    }

    public int getGarage() {
        return garage;
    }
    
    

}

