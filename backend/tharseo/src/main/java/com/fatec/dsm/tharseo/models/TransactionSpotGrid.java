package com.fatec.dsm.tharseo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_transaction_spotgrid")
public class TransactionSpotGrid  extends Transaction {

    private Double priceTarget;
    private LocalDateTime closeDate;
    private Double profit;

    public TransactionSpotGrid() {
    }

    public TransactionSpotGrid(Double priceTarget, LocalDateTime closeDate, Double profit) {
        this.priceTarget = priceTarget;
        this.closeDate = closeDate;
        this.profit = profit;
    }



    public Double getPriceTarget() {
        return priceTarget;
    }

    public void setPriceTarget(Double priceTarget) {
        this.priceTarget = priceTarget;
    }

    public LocalDateTime getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDateTime closeDate) {
        this.closeDate = closeDate;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    @Override
    public String toString() {
        return "TransactionSpotGrid{" +
                "priceTarget=" + priceTarget +
                ", closeDate=" + closeDate +
                ", profit=" + profit +
                '}';
    }
}
