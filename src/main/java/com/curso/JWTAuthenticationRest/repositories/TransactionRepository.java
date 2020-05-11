package com.curso.JWTAuthenticationRest.repositories;

import com.curso.JWTAuthenticationRest.model.Transaction_History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction_History,Integer> {
    List<Transaction_History> findByUsername(String username);
    List<Transaction_History> findByUsernameAndTxnTypeIsNotContaining(String username,String  str);
    List<Transaction_History> findByUsernameAndTxnTypeIsContaining(String username,String  str);
}