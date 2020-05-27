package com.curso.JWTAuthenticationRest;

import com.curso.JWTAuthenticationRest.exception.NotHavingSufficentBalance;
import com.curso.JWTAuthenticationRest.model.Transaction_History;
import com.curso.JWTAuthenticationRest.repositories.AccountRepository;
import com.curso.JWTAuthenticationRest.repositories.TransactionRepository;
import com.curso.JWTAuthenticationRest.services.TransactionService;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.context.ContextConfiguration;

import static com.curso.JWTAuthenticationRest.constants.Constants.DEPOSIT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@EnableRetry
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes={TransactionService.class})
@ContextConfiguration(classes= RetryTestApplicationTests.MyConfig.class)
class RetryTestApplicationTests {

    @Configuration
    public static class MyConfig {

        private AccountRepository mockAccountRepository = Mockito.mock(AccountRepository.class);
        private TransactionRepository mockTransactionRepository = Mockito.mock(TransactionRepository.class);

        @Bean
        public AccountRepository getAccountBean() {
            System.out.println("A");
            return mockAccountRepository;
        }
        @Bean
        public TransactionRepository getTransactionBean() {
            System.out.println("B");
            return mockTransactionRepository;
        }
    }

    @Autowired
    private MyConfig myConfig;

    //@Mock
    private AccountRepository accountRepository;

    //@Mock
    private TransactionRepository transactionRepository;

    @Autowired
    private  com.curso.JWTAuthenticationRest.services.TransactionService transactionService;

    @BeforeEach
    public void setup() {
        accountRepository = myConfig.getAccountBean();
        transactionRepository = myConfig.getTransactionBean();
    }

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
        // transactionService.setBalanceOfUser("yatharth", "Deposit", 273.00);
    }

    @Test
    void test1 () throws NotHavingSufficentBalance{
        System.out.println(accountRepository==null);
        when(accountRepository.numberOfRRowUpdateForDeposit(273.00, "yatharth")).thenReturn(1);
        doAnswer(invocation -> {
            Transaction_History history = invocation.getArgument(0);
            System.out.println("Attempt");
            if (history.getTxnType().equals(DEPOSIT) && history.getAmount().equals(273d) && history.getUsername().equals("yatharth"))
                throw new RuntimeException("couldn't process record");
            return null;
        }).when(transactionRepository).save(any());
        try {
            transactionService.withdraw_and_update_transaction("yatharth", "Deposit", 273.00,"good");
            fail();
        } catch (RuntimeException e) {
            assertEquals("couldn't process record", e.getMessage());
        }
        verify(accountRepository, times(4)).numberOfRRowUpdateForDeposit(273.00d, "yatharth");
        verify(transactionRepository, times(4)).save(any());
    }
}