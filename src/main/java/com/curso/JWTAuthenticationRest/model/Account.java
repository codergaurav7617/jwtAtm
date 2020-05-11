package com.curso.JWTAuthenticationRest.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Account {
    @Id
    @GeneratedValue
    @Getter @Setter
    private int accountId;
    @Getter @Setter
    private String username;

    private Double amount=0.0;

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