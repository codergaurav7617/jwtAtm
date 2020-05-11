package com.curso.JWTAuthenticationRest.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Transcation_Type {
    @Id
    private String txn_type;
}
