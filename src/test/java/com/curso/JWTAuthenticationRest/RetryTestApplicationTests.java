package com.curso.JWTAuthenticationRest;

import com.curso.JWTAuthenticationRest.exception.NotHavingSufficentBalance;
import com.curso.JWTAuthenticationRest.repositories.AccountRepository;
import com.curso.JWTAuthenticationRest.repositories.TransactionRepository;
import com.curso.JWTAuthenticationRest.services.TransactionService;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@EnableRetry
@RunWith(SpringRunner.class)
@SpringBootTest(classes={TransactionService.class})
@ContextConfiguration(classes= RetryTestApplicationTests.MyConfig.class)
class RetryTestApplicationTests {

    @Configuration
    public static class MyConfig {

        @Bean
        public AccountRepository getAccountBean() {
            System.out.println("A");
            return Mockito.mock(AccountRepository.class);
        }
        @Bean
        public TransactionRepository getTransactionBean()
        {
            System.out.println("B");
            return Mockito.mock(TransactionRepository.class);
        }
    }

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    private final TransactionService transactionService=new TransactionService(accountRepository, transactionRepository);


    @After
    public void tearDown() {
        verifyNoMoreInteractions(transactionRepository, accountRepository);
    }

    @Test
    void test() throws NotHavingSufficentBalance {
        when(accountRepository.numberOfRRowUpdateForDeposit(273.00, "yatharth")).thenReturn(1);
        System.out.println(accountRepository);
        System.out.println(transactionRepository);
        System.out.println(transactionService);
        transactionService.setBalanceOfUser("yatharth", "Deposit", 273.00);
    }

    @Test
    void test1(){
        transactionService.retryService();
    }
}
