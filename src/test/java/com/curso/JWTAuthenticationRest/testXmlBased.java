package com.curso.JWTAuthenticationRest;

import com.curso.JWTAuthenticationRest.exception.NotHavingSufficentBalance;
import com.curso.JWTAuthenticationRest.repositories.AccountRepository;
import com.curso.JWTAuthenticationRest.services.LoginService;
import com.curso.JWTAuthenticationRest.services.TransactionService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/spring-config.xml")
public class testXmlBased {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    @Test
    public void shouldInjectService() throws NotHavingSufficentBalance {
        System.out.println(accountRepository);
        System.out.println(accountRepository.findByUsername("987654"));
        transactionService.withdraw_and_update_transaction("987654", "Deposit", 120.00, "hi");
        System.out.println(accountRepository.findByUsername("987654"));
        System.out.println(accountRepository==null);
    }
}
