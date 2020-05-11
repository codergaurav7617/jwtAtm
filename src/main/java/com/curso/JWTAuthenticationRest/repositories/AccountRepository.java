package com.curso.JWTAuthenticationRest.repositories;

import com.curso.JWTAuthenticationRest.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface AccountRepository extends JpaRepository<Account,Integer> {
    Account findByUsername(String username);
}
