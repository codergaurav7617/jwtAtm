package com.curso.JWTAuthenticationRest;

import com.curso.JWTAuthenticationRest.config.H2TestProfile;
import com.curso.JWTAuthenticationRest.exception.NotHavingSufficentBalance;
import com.curso.JWTAuthenticationRest.repositories.AccountRepository;
import com.curso.JWTAuthenticationRest.repositories.TransactionRepository;
import com.curso.JWTAuthenticationRest.services.TransactionService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ExtendWith(SpringExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@ContextConfiguration(classes = actualDbTest.MyConfig.class)
public class actualDbTest {

//    @Configuration
//    public static class MyConfig {
//
//        @Autowired
//        private AccountRepository accountRepository;
//        @Autowired
//        private TransactionService transactionService;
//
//        @Bean
//        public AccountRepository getAccountBean() {
//            System.out.println("A");
//            return accountRepository;
//        }
//        @Bean
//        public TransactionService getTransactionBean() {
//            System.out.println("B");
//            return transactionService;
//        }
//    }
//
//
//    @Autowired
//    private MyConfig myConfig;


   @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

//    @BeforeEach
//    public void setup() {
//        accountRepository = myConfig.getAccountBean();
//        transactionService = myConfig.getTransactionBean();
//    }
//

    @Test
    public void shouldInjectService() throws NotHavingSufficentBalance {
        System.out.println(accountRepository);
        System.out.println(accountRepository.findByUsername("987654"));
        transactionService.withdraw_and_update_transaction("987654", "Deposit", 120.00, "hi");
        System.out.println(accountRepository.findByUsername("987654"));
        System.out.println(accountRepository==null);
    }

}
