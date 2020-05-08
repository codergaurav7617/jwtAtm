package com.curso.JWTAuthenticationRest.controller;

import com.curso.JWTAuthenticationRest.model.JwtUser;
import com.curso.JWTAuthenticationRest.model.Login;
import com.curso.JWTAuthenticationRest.security.JwtGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/token")
public class TokenController {

    private JwtGenerator jwtGenerator;

    public TokenController(JwtGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping
    public ResponseEntity<?> generate( Login login) {
        JwtUser jwtUser = new JwtUser();

        jwtUser = existUser(login);

        if(jwtUser != null) {
            List<String> list = new ArrayList<>();
            list.add(jwtGenerator.generate(jwtUser));
            return new ResponseEntity<List<String>>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    private JwtUser existUser(Login login) {
        if(login.getUser().equals("manuel") && login.getPassword().equals("1234")) {
            JwtUser jwtUser = new JwtUser();
            jwtUser.setUsername(login.getUser());
            jwtUser.setId(1);
            jwtUser.setRole("Admin");
            return jwtUser;
        }else  {
            return null;
        }
    }

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
}