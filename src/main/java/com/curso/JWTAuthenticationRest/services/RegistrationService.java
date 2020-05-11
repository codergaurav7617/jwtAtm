package com.curso.JWTAuthenticationRest.services;

import com.curso.JWTAuthenticationRest.model.Login;
import com.curso.JWTAuthenticationRest.repositories.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    @Autowired
    private LoginRepository loginRepository;

    public void createUser(String userName,String password){
        Login user=new Login(userName,password);
        loginRepository.save(user);
    }
}
