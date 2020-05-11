package com.curso.JWTAuthenticationRest.controller;

import com.curso.JWTAuthenticationRest.model.Login;
import com.curso.JWTAuthenticationRest.repositories.LoginRepository;
import com.curso.JWTAuthenticationRest.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @RequestMapping("/register")
    public boolean register(
            @RequestParam String user,
            @RequestParam String password
    ){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        registrationService.createUser(user, encodedPassword);
        return true;
    }
}
