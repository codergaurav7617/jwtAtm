package com.curso.JWTAuthenticationRest.config;

import com.curso.JWTAuthenticationRest.repositories.AccountRepository;
import com.curso.JWTAuthenticationRest.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class H2TestProfile {


    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionService transactionService;

    @Bean
    public TransactionService getTransactionBean(){
        return transactionService;
    }

    @Bean
    public AccountRepository getAccountBean() {
       return accountRepository;
    }
}
