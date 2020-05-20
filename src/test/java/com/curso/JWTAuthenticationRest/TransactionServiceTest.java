package com.curso.JWTAuthenticationRest;

import com.curso.JWTAuthenticationRest.exception.NotHavingSufficentBalance;
import com.curso.JWTAuthenticationRest.model.Account;
import com.curso.JWTAuthenticationRest.model.Transaction_History;
import com.curso.JWTAuthenticationRest.repositories.AccountRepository;
import com.curso.JWTAuthenticationRest.repositories.TransactionRepository;
import com.curso.JWTAuthenticationRest.services.TransactionService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.retry.annotation.EnableRetry;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
@EnableRetry
@SpringBootTest(classes={TransactionService.class})
@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    private TransactionService transactionService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Before
    public void setup() {
        System.out.println("abc");
        transactionService =  new TransactionService(accountRepository, transactionRepository);
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(transactionRepository, accountRepository);
    }

    @Test
    public void getHistoryTest(){
        when(transactionRepository.findByUsername("gaurav")).thenReturn(Stream
                .of(new Transaction_History("gaurav",273.0,"loan","Deposit"),
                        new Transaction_History("gaurav",213.0,"education","Deposit")).collect(Collectors.toList()));
          assertEquals(
                  2,transactionService.getHistory("gaurav").size());
          verify(transactionRepository).findByUsername("gaurav");
    }

    @Test
    public void getHistoryTestWhenUserNameAreNotSame(){
        when(transactionRepository.findByUsername("gaurav")).thenReturn(Stream
                .of(new Transaction_History("gaurav",273.0,"loan","Deposit"),
                        new Transaction_History("gaurav",213.0,"education","Deposit")).collect(Collectors.toList()));
        assertEquals(
                2,transactionService.getHistory("gaurav").size());
        verify(transactionRepository).findByUsername("gaurav");
    }

    @Test
    public void getAllTheViewTransactionTest(){
           when(transactionRepository.findByUsernameAndTxnTypeIsContaining("piyush","view")).thenReturn(Stream
                   .of(new Transaction_History("piyush",0.0,"","view"),
                           new Transaction_History("piyush",0.0,"","view"),
                           new Transaction_History("piyush",0.0,"","view")
                           ).collect(Collectors.toList()));
        assertEquals(
                3,transactionService.getAllTheViewTransaction("piyush").size());
        verify(transactionRepository).findByUsernameAndTxnTypeIsContaining("piyush", "view");
    }

    @Test
    public void getAllTheViewTransactionTestWhenUserNameAreNotSame(){
        when(transactionRepository.findByUsernameAndTxnTypeIsContaining("piyush","view")).thenReturn(Stream
                .of(new Transaction_History("piyush",0.0,"","view"),
                        new Transaction_History("piyush",0.0,"","view"),
                        new Transaction_History("piyush",0.0,"","view")
                ).collect(Collectors.toList()));
        assertEquals(
                3,transactionService.getAllTheViewTransaction("piyush").size());
        verify(transactionRepository).findByUsernameAndTxnTypeIsContaining("piyush", "view");
    }

    @Test
    public void getAllTheViewTransactionTestHavingSomeMixedTypeOfTransaction(){
        when(transactionRepository.findByUsernameAndTxnTypeIsContaining("piyush","view")).thenReturn(Stream
                .of(new Transaction_History("piyush",0.0,"","view"),
                        new Transaction_History("piyush",0.0,"","withdraw"),
                        new Transaction_History("piyush",0.0,"","Deposit"),
                        new Transaction_History("gaurav",0.0,"","Deposit")
                ).collect(Collectors.toList()));
        assertEquals(
                4,transactionService.getAllTheViewTransaction("piyush").size());
               verify(transactionRepository).findByUsernameAndTxnTypeIsContaining("piyush", "view");
    }

    @Test
    public void getAllTheTransactionTest(){
        when(transactionRepository.findByUsernameAndTxnTypeIsNotContaining("yatharth","view")).thenReturn(Stream
                .of(new Transaction_History("yatharth",0.0,"","view"),
                        new Transaction_History("yatharth",172.0,"","withdraw"),
                        new Transaction_History("yatharth",170.0,"","Deposit"),
                        new Transaction_History("yatharth",10.0,"","Deposit")
                ).collect(Collectors.toList()));
        assertEquals(
                4,transactionService.getAllTheTransaction("yatharth").size());
        verify(transactionRepository).findByUsernameAndTxnTypeIsNotContaining("yatharth", "view");
    }

    @Test
    public void testSetBalanceOfUser() throws NotHavingSufficentBalance {
        when(accountRepository.numberOfRRowUpdateForDeposit(273.00, "yatharth")).thenReturn(1);
        transactionService.setBalanceOfUser("yatharth","Deposit" ,273.00 );
        verify(accountRepository).numberOfRRowUpdateForDeposit(273.00, "yatharth");
    }

    @Test
    public void test() throws NotHavingSufficentBalance {
        when(accountRepository.numberOfRRowUpdateForDeposit(273.00, "yatharth")).thenReturn(1);
        transactionService.setBalanceOfUser("yatharth","Deposit" ,273.00 );
    }

    @Test
    public void test1(){
        transactionService.retryService();
    }

}
