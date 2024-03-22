package com.fatec.dsm.tharseo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name = "tb_transactions")
public class Transaction implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long orderId;
    private Double origQty;
    private Double executedQty;
    private String side;
    @JsonIgnore
    @ManyToOne
    private User user;
    @ManyToOne
    private Asset asset;
    private Double price;
    private Double stopPrice;
    private LocalDateTime openDate;
    private String typeTransaction;
    private Boolean openTrade;
    private Integer isActive;

    public Transaction() {

    }

    public Transaction(Long id, User user, Asset asset, Double price, String typeTransaction, Integer isActive) {
        this.id = id;
        this.user = user;
        this.asset = asset;
        this.price = price;
        this.openDate = LocalDateTime.now();
        this.typeTransaction = typeTransaction;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getOpenDate() {
        return openDate;
    }

    public void setOpenDate(LocalDateTime openDate) {
        this.openDate = openDate;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(String typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Double getOrigQty() {
        return origQty;
    }

    public void setOrigQty(Double origQty) {
        this.origQty = origQty;
    }

    public Double getExecutedQty() {
        return executedQty;
    }

    public void setExecutedQty(Double executedQty) {
        this.executedQty = executedQty;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public Double getStopPrice() {
        return stopPrice;
    }

    public void setStopPrice(Double stopPrice) {
        this.stopPrice = stopPrice;
    }

    public Boolean getOpenTrade() {
        return openTrade;
    }

    public void setOpenTrade(Boolean openTrade) {
        this.openTrade = openTrade;
    }
}
