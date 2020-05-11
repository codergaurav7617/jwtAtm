package com.curso.JWTAuthenticationRest.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Transcation_Type {
    @Id
    @Getter@Setter
    private String txn_type;
}
