package com.curso.JWTAuthenticationRest;

import com.curso.JWTAuthenticationRest.config.H2TestProfile;
import com.curso.JWTAuthenticationRest.exception.NotHavingSufficentBalance;
import com.curso.JWTAuthenticationRest.repositories.AccountRepository;
import com.curso.JWTAuthenticationRest.services.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = H2TestProfile.class)
public class actualDbTest {
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
