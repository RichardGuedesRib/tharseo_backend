package com.fatec.dsm.tharseo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_strategygriduser")

public class StrategyGridUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "iduser")
    private User user;
    private String acronym;
    private String configStrategy;
    private Double profit;
    private Double performance;
    private Integer isActive;


    public StrategyGridUser() {
    }


    public StrategyGridUser(Long id, User user, String acronym, String configStrategy, Double profit, Double performance, Integer isActive) {
        this.id = id;
        this.user = user;
        this.acronym = acronym;
        this.configStrategy = configStrategy;
        this.profit = profit;
        this.performance = performance;
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

    public String getConfigStrategy() {
        return configStrategy;
    }

    public void setConfigStrategy(String configStrategy) {
        this.configStrategy = configStrategy;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Double getPerformance() {
        return performance;
    }

    public void setPerformance(Double performance) {
        this.performance = performance;
    }

    @Override
    public String toString() {
        return "StrategyGridUser{" +
                "id=" + id +
                ", user=" + user +
                ", acronym='" + acronym + '\'' +
                ", configStrategy='" + configStrategy + '\'' +
                ", profit=" + profit +
                ", performance=" + performance +
                ", isActive=" + isActive +
                '}';
    }
}
