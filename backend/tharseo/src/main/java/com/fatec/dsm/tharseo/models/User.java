package com.fatec.dsm.tharseo.models;


import com.fatec.dsm.tharseo.models.Transaction;
import jakarta.persistence.*;


import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
        private List<Asset> wallet;

        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
        private List<Transaction> transactions = new ArrayList<>();

        private Integer isactive;

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

        public List<Asset> getWallet() {
            return wallet;
        }

        public void setWallet(List<Asset> wallet) {
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
}
