package com.curso.JWTAuthenticationRest.controller;

import com.curso.JWTAuthenticationRest.model.JwtUser;
import com.curso.JWTAuthenticationRest.model.Login;
import com.curso.JWTAuthenticationRest.repositories.LoginRepository;
import com.curso.JWTAuthenticationRest.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import static com.curso.JWTAuthenticationRest.constants.Constants.ADMIN;
import static com.curso.JWTAuthenticationRest.constants.Constants.ID;

@RestController
@RequestMapping("/token")
public class TokenController {
    private JwtGenerator jwtGenerator;
    @Autowired
    private LoginRepository loginRepository;

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

       // for finding out the user from the db on the basis of user name
        Login l=loginRepository.findByUser(login.getUser());
        // for password matching
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

        boolean isPasswordMatches=passwordEncoder.matches(login.getPassword(), l.getPassword());

        if(login.getUser().equals(l.getUser()) && isPasswordMatches) {
            JwtUser jwtUser = new JwtUser();
            jwtUser.setUsername(login.getUser());
            jwtUser.setId(ID);
            jwtUser.setRole(ADMIN);
            return jwtUser;
        }else  {
            return null;
        }
    }

}