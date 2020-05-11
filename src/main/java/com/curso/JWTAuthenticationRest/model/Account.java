package com.curso.JWTAuthenticationRest.model;

import javax.persistence.Entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Account {
    @Id
    @GeneratedValue
    private int accountId;

    private String username;

    private Double amount=0.0;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getAmount() {
        return amount;
    }

    public Account(){};

    public Account(String username){
        this.username=username;
    }

    public void setAmount(Double amount) {
        System.out.println(amount);
        this.amount += amount;
    }

}