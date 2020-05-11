package com.curso.JWTAuthenticationRest.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static com.curso.JWTAuthenticationRest.constants.Constants.FORMATTER;
import java.util.Date;
@Entity
public class Transaction_History {
    @Id
    @GeneratedValue
    @Getter @Setter
    private int txn_id;
    @Getter @Setter
    private String username;
    @Getter @Setter
    private Double amount;
    @Getter @Setter
    private String time_stamp;
    @Getter @Setter
    private String comment;
    @Getter @Setter
    private String txnType;

    public Transaction_History(){}

    public Transaction_History(String username,Double amount,String comment,String txn_type){

        this.username=username;
        this.amount=amount;
        this.time_stamp=FORMATTER.format(new Date());
        this.txnType=txn_type;
        this.comment=comment;
    }

}