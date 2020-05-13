package com.curso.JWTAuthenticationRest.services;

import com.curso.JWTAuthenticationRest.model.JwtUser;
import com.curso.JWTAuthenticationRest.model.Login;
import com.curso.JWTAuthenticationRest.repositories.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

import static com.curso.JWTAuthenticationRest.constants.Constants.ADMIN;
import static com.curso.JWTAuthenticationRest.constants.Constants.ID;

@Service
public class LoginService {
     @Autowired
    private LoginRepository loginRepository;


    public JwtUser existUser(Login login) {
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

    public Cookie create_cookie(String name, String value){

        // for creation of cookies storing in the browser and it is send in the every request(small piece of info store in the browser).
        Cookie cookie = new Cookie(name,value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(-40);
        cookie.setPath("/");
        return cookie;
    }
}
