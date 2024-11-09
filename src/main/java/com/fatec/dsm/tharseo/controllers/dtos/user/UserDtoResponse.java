package com.fatec.dsm.tharseo.controllers.dtos.user;

import com.fatec.dsm.tharseo.models.AssetsUser;
import com.fatec.dsm.tharseo.models.StrategyGridUser;
import com.fatec.dsm.tharseo.models.Transaction;
import com.fatec.dsm.tharseo.models.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDtoResponse {
    private Long id;
    private String name;
    private String lastname;
    private String phoneNumber;
    private String email;
    private Integer isactive;
    private String avatar;
    private List<AssetsUser> wallet;
    private List<StrategyGridUser> grids;
    private List<Transaction> transactions;


    public UserDtoResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.isactive = user.getIsactive();
        this.avatar = user.getAvatar();

        this.wallet = user.getWallet();
        this.grids = user.getGrids();
        this.transactions = user.getTransactions();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIsactive() {
        return isactive;
    }

    public void setIsactive(Integer isactive) {
        this.isactive = isactive;
    }

    public List<AssetsUser> getWallet() {
        return wallet;
    }

    public void setWallet(List<AssetsUser> wallet) {
        this.wallet = wallet;
    }

    public List<StrategyGridUser> getGrids() {
        return grids;
    }

    public void setGrids(List<StrategyGridUser> grids) {
        this.grids = grids;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
