package com.curso.JWTAuthenticationRest;

import com.curso.JWTAuthenticationRest.exception.NotHavingSufficentBalance;
import com.curso.JWTAuthenticationRest.model.Transaction_History;
import com.curso.JWTAuthenticationRest.repositories.AccountRepository;
import com.curso.JWTAuthenticationRest.repositories.TransactionRepository;
import com.curso.JWTAuthenticationRest.services.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.retry.annotation.EnableRetry;
import static org.mockito.Mockito.*;
@EnableRetry
@SpringBootTest(classes={TransactionService.class})
@RunWith(MockitoJUnitRunner.class)
public class justtest {

    private TransactionService transactionService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Before
    public void setup() {
        transactionService =  new TransactionService(accountRepository, transactionRepository);
    }

    @Test
    public void testSetBalanceOfUser() throws NotHavingSufficentBalance {
        when(accountRepository.numberOfRRowUpdateForDeposit(273.00, "yatharth")).thenReturn(1);
        Transaction_History th=new Transaction_History("Yatharth", 273.00, "good", "Deposit");
        System.out.println(th);
        when(transactionRepository.save(new Transaction_History())).thenThrow(RuntimeException.class);
        transactionService.withdraw_and_update_transaction("yatharth", "Deposit", 273.00,"good");
    }
}
