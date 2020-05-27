package com.curso.JWTAuthenticationRest.controller;

import com.curso.JWTAuthenticationRest.model.JwtUser;
import com.curso.JWTAuthenticationRest.model.Login;
import com.curso.JWTAuthenticationRest.security.JwtGenerator;
import com.curso.JWTAuthenticationRest.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import static com.curso.JWTAuthenticationRest.constants.Constants.*;

@RestController
@RequestMapping("/token")
public class TokenController {

    private JwtGenerator jwtGenerator;

    @Autowired
    private LoginService loginService;

    public TokenController(JwtGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping
    public ResponseEntity<?> generate(Login login,HttpServletResponse response) {
        System.out.println(login);
        System.out.println(login.getUser()+" "+login.getPin());
        JwtUser jwtUser = new JwtUser();
        jwtUser = loginService.existUser(login);
        if(jwtUser != null) {
            List<String> list = new ArrayList<>();
            list.add(jwtGenerator.generate(jwtUser));
            response.addCookie(loginService.create_cookie(TOKEN, list.get(0)));
            return new ResponseEntity<List<String>>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public ModelAndView Logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null); // Not necessary, but saves bandwidth.
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return new ModelAndView("login");
    }


}