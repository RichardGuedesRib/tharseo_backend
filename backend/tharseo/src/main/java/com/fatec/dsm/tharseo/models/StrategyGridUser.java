package com.fatec.dsm.tharseo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_strategygriduser")

public class StrategyGridUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    private String configStrategy;

    private Integer isActive;

    public StrategyGridUser() {
    }

    public StrategyGridUser(Long id, User user, String configStrategy, Integer isActive) {
        this.id = id;
        this.user = user;
        this.configStrategy = configStrategy;
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
}
