package com.fatec.dsm.tharseo.models;

public class AssetPrice {
    private String symbol;
    private Double price;

    public AssetPrice() {
    }

    public AssetPrice(String symbol, Double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
