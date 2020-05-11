package com.curso.JWTAuthenticationRest.repositories;

import com.curso.JWTAuthenticationRest.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface LoginRepository extends JpaRepository<Login,Integer> {
       Login findByUser(String user);
}
