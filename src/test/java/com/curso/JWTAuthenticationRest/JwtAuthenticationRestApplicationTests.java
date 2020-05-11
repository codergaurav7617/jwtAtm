package com.curso.JWTAuthenticationRest;

import com.curso.JWTAuthenticationRest.model.Transaction_History;
import com.curso.JWTAuthenticationRest.repositories.TransactionRepository;
import com.curso.JWTAuthenticationRest.services.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtAuthenticationRestApplicationTests {
    @Autowired
    private TransactionService transactionService;

    @MockBean
    private TransactionRepository transactionRepository;

    @Test
    public void getHistoryTest(){
        when(transactionRepository.findByUsername("gaurav")).thenReturn(Stream
                .of(new Transaction_History("gaurav",273.0,"loan","Deposit"),
                        new Transaction_History("gaurav",213.0,"education","Deposit")).collect(Collectors.toList()));
          assertEquals(
                  2,transactionService.getHistory("gaurav").size());
    }

    @Test
    public void getHistoryTestWhenUserNameAreNotSame(){
        when(transactionRepository.findByUsername("gaurav")).thenReturn(Stream
                .of(new Transaction_History("gaurav",273.0,"loan","Deposit"),
                        new Transaction_History("gaurav",213.0,"education","Deposit")).collect(Collectors.toList()));
        assertEquals(
                0,transactionService.getHistory("piyush").size());
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
    }

    @Test
    public void getAllTheViewTransactionTestWhenUserNameAreNotSame(){
        when(transactionRepository.findByUsernameAndTxnTypeIsContaining("piyush","view")).thenReturn(Stream
                .of(new Transaction_History("piyush",0.0,"","view"),
                        new Transaction_History("piyush",0.0,"","view"),
                        new Transaction_History("piyush",0.0,"","view")
                ).collect(Collectors.toList()));
        assertEquals(
                0,transactionService.getAllTheViewTransaction("gaurav").size());
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
    }

    @Test
    public void getAllTheTransactionTestWhenUserNameAreNotSame(){
        when(transactionRepository.findByUsernameAndTxnTypeIsNotContaining("yatharth","view")).thenReturn(Stream
                .of(new Transaction_History("yatharth",0.0,"","view"),
                        new Transaction_History("yatharth",172.0,"","withdraw"),
                        new Transaction_History("yatharth",170.0,"","Deposit"),
                        new Transaction_History("yatharth",10.0,"","Deposit")
                ).collect(Collectors.toList()));
        assertEquals(
                0,transactionService.getAllTheTransaction("piyush").size());

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

    }

}
