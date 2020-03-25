package com.curso.JWTAuthenticationRest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SecurityController {

    @GetMapping("/access_only_with_jwt")
    public ResponseEntity<?> getBankInformation() {
        List<String> bankingTransactions = getBankingTransactions();

        if(bankingTransactions.size() != 0) {
            return new ResponseEntity<>(bankingTransactions, HttpStatus.OK);
        } else{
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    private List<String> getBankingTransactions() {
        List<String> bankingTransactions = new ArrayList<>();
        bankingTransactions.add("20");
        bankingTransactions.add("1200");
        bankingTransactions.add("60");
        bankingTransactions.add("-200");
        bankingTransactions.add("-500");

        return bankingTransactions;
    }

}
