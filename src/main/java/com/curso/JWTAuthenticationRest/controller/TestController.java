package com.curso.JWTAuthenticationRest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @RequestMapping(value = "/" , method = RequestMethod.GET)
    @Retryable(value = {NumberFormatException.class , NullPointerException.class}, maxAttempts = 5)
    public String myApp(){
        System.out.println("My Api is calling : ");
        String str=null;
        str.length();
        return "sucess";
    }

    @Recover
    public String recover(NumberFormatException ex){
        System.out.println("Recover method - > number format exception");
        return "Recover method - Number Format Exception";
    }


    @Recover
    public String recover(NullPointerException ex){
        System.out.println("Recover method - > Null pointer exception");
        return "Recover method - Null pointer Exception";
    }

}
