package com.fatec.dsm.tharseo.models;

import jakarta.persistence.Entity;

@Entity
public class AssetsUser extends Asset {

    private Double price;

    public AssetsUser() {}

    public AssetsUser(Long id, String name, String acronym, Double quantity, Integer isActive) {
        super(id, name, acronym, quantity, isActive);
    }

    public AssetsUser(Long id, String name, String acronym, Double quantity, Integer isActive, Double price) {
        super(id, name, acronym, quantity, isActive);
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
