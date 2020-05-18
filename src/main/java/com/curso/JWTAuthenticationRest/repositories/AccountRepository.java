package com.curso.JWTAuthenticationRest.repositories;

import com.curso.JWTAuthenticationRest.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.retry.annotation.Recover;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Transactional
@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {

    Account findByUsername(String username);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Account a SET a.amount = a.amount- :balance WHERE a.username=:username AND (a.amount-:balance) >= 0")
    int  numberOfRRowUpdateForWithdrawal(@Param("balance") Double balance, @Param("username") String username);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Account a SET a.amount = a.amount+ :balance WHERE a.username=:username")
    int numberOfRRowUpdateForDeposit(@Param("balance") Double balance, @Param("username") String username);

}
