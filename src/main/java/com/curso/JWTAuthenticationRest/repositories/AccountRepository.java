package com.curso.JWTAuthenticationRest.repositories;

import com.curso.JWTAuthenticationRest.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

//@Transactional  // can be removed try to use in the method level
@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {

    Account findByUsername(String username);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Account a SET a.amount = a.amount- :balance WHERE a.username=:username AND (a.amount-:balance) >= 0")
    int  numberOfRRowUpdateForWithdrawal(@Param("balance") Double balance, @Param("username") String username);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Account a SET a.amount = a.amount+ :balance WHERE a.username=:username")
    int numberOfRRowUpdateForDeposit(@Param("balance") Double balance, @Param("username") String username);

    /*
     write a method withdraw and update transaction
     withdraw money
     if withdrawing fails after retry  send error to the user
     if sucess then u update the transaction
     if update transcation fails after retry then rollback withdrawal and send error to the user
     */

     // atm screen

}
