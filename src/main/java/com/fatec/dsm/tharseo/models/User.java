package com.fatec.dsm.tharseo.models;


import com.fatec.dsm.tharseo.dtos.AuthenticateRequest;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tb_users")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String lastname;
    private String phoneNumber;
    private String email;
    private String password;
    @OneToMany(mappedBy = "user")
    private List<AssetsUser> wallet;
    @OneToMany(mappedBy = "user")
    private List<StrategyGridUser> grids;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Transaction> transactions = new ArrayList<>();
    private String apiKey;
    private String secretKey;
    private Integer isactive;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public User() {
        this.wallet = new ArrayList<>();
    }

    public User(Long id, String name, String lastname, String phoneNumber, String email, String password, Integer isactive) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.wallet = new ArrayList<>();
        this.isactive = isactive;
    }

    public User(Long id, String name, String lastname, String phoneNumber, String email, String password, Integer isactive, String apiKey, String secretKey) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.wallet = new ArrayList<>();
        this.isactive = isactive;
        this.apiKey = apiKey;
        this.secretKey = secretKey;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<AssetsUser> getWallet() {
        return wallet;
    }

    public void setWallet(List<AssetsUser> wallet) {
        this.wallet = wallet;
    }

    public Integer getIsactive() {
        return isactive;
    }

    public void setIsactive(Integer isactive) {
        this.isactive = isactive;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addAsset(AssetsUser asset) {
        this.wallet.add(asset);
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public List<StrategyGridUser> getGrids() {
        return grids;
    }

    public void setGrids(List<StrategyGridUser> grids) {
        this.grids = grids;
    }

    public void addGrid(StrategyGridUser strategyGridUser) {
        this.grids.add(strategyGridUser);
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


}
