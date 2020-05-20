package com.curso.JWTAuthenticationRest.controller;

import com.curso.JWTAuthenticationRest.exception.RegistrationFailure;
import com.curso.JWTAuthenticationRest.model.Login;
import com.curso.JWTAuthenticationRest.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @RequestMapping("/register")
    public boolean register(
            Login login
    ) throws RegistrationFailure {
        System.out.println("abc");
        System.out.println(login);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(login.getPassword());
        registrationService.createUser(login.getUser(), encodedPassword);
        return true;
    }
}
