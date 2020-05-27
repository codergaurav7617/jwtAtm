package com.curso.JWTAuthenticationRest.services;

import com.curso.JWTAuthenticationRest.model.JwtUser;
import com.curso.JWTAuthenticationRest.model.Login;
import com.curso.JWTAuthenticationRest.repositories.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;

import static com.curso.JWTAuthenticationRest.constants.Constants.*;

@Service
public class LoginService {

    @Value("${cookieExpiration}")
        private int cookieExpiration;

    @Autowired
    private LoginRepository loginRepository;

    public JwtUser existUser(Login login) {
        Login l=loginRepository.findByUser(login.getUser());
        // for password matching
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        boolean isPasswordMatches=passwordEncoder.matches(login.getPin()+"", l.getPin());
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
        cookie.setMaxAge(-cookieExpiration);
        cookie.setPath("/");
        return cookie;
    }

    public ModelAndView getView(){
        ModelAndView mv=new ModelAndView();
        mv.setViewName(TRANSACTIONTYPE);
        return mv;
    }

}
