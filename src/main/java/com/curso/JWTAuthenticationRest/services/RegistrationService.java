package com.curso.JWTAuthenticationRest.services;

import com.curso.JWTAuthenticationRest.exception.RegistrationFailure;
import com.curso.JWTAuthenticationRest.model.Account;
import com.curso.JWTAuthenticationRest.model.Login;
import com.curso.JWTAuthenticationRest.repositories.AccountRepository;
import com.curso.JWTAuthenticationRest.repositories.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final LoginRepository loginRepository;

    private final AccountRepository accountRepository;

    @Autowired
    public RegistrationService(LoginRepository loginRepository,AccountRepository accountRepository){
        this.loginRepository=loginRepository;
        this.accountRepository=accountRepository;
    }

    public void createUser(String userName,String password) throws RegistrationFailure {

        // check if other user with the same name exist or not
        Login isExist =loginRepository.findByUser(userName);

        if (isExist==null){
            Login user=new Login(userName,password);
            Account account_of_user=new Account(userName);
            loginRepository.save(user);
            accountRepository.save(account_of_user);
        }else{
            throw new RegistrationFailure("please enter new user id");
        }
    }
}
