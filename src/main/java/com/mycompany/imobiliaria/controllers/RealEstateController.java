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
    
    public enum SortField {
        ID("id", "ID"),
        ADDRESS("address", "Endereço"),
        NEIGHBORHOOD("neighborhood", "Bairro"),
        NUMBER("number", "Número"),
        CITY("city", "Cidade"),
        TYPE("type", "Tipo"),
        ROOMS("rooms", "Quartos"),
        BATHROOMS("bathrooms", "Banheiros"),
        AREA("area", "Área"),
        VALUE("value", "Valor"),
        GARAGE("garage", "Garagem");

        private final String fieldName;
        private final String displayName;

        SortField(String fieldName, String displayName) {
            this.fieldName = fieldName;
            this.displayName = displayName;
        }

        public String getFieldName() {
            return fieldName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public static SortField fromFieldName(String fieldName) {
            for (SortField sf : SortField.values()) {
                if (sf.getFieldName().equalsIgnoreCase(fieldName)) {
                    return sf;
                }
            }
            throw new IllegalArgumentException("Unknown field: " + fieldName);
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    public enum SortDirection {
        ASC,
        DESC
    }

    public List<RealEstateModel> getAllProperties(SortField sortField, SortDirection sortDirection) {
        System.out.println(sortField.getFieldName());
        return dao.getAll(sortField.getFieldName(), sortDirection.name());
    }

    public RealEstateModel getPropertyById(int id) {
        return dao.getById(id);
    }
}
