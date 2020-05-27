package com.curso.JWTAuthenticationRest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Login {
    @Id
    @GeneratedValue
    @Getter@Setter
    private int id;
    @Getter@Setter
    private String user;
    @Getter @Setter
    private String pin;

    public Login(){}

    public Login(String user,String pin){
        this.user=user;
        this.pin=pin;
    }
}
