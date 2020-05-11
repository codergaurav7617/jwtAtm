package com.curso.JWTAuthenticationRest.controller;

import com.curso.JWTAuthenticationRest.model.Login;
import com.curso.JWTAuthenticationRest.repositories.AccountRepository;
import com.curso.JWTAuthenticationRest.repositories.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class RegistrationController {

    @Autowired
    private LoginRepository loginRepository;

    @RequestMapping("/register")
    public boolean register(
            @RequestParam String user,
            @RequestParam String password
    ){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
           Login l=new Login(user,encodedPassword);
           loginRepository.save(l);
       return true;
    }
}
