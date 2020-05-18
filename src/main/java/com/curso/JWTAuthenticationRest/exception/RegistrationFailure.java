package com.curso.JWTAuthenticationRest.exception;

public class RegistrationFailure extends Exception {

    private static final long serialVersionUID = -470180507998010368L;

    public RegistrationFailure(){
        super();
    }
    public RegistrationFailure(final String message){
        super(message);
    }
}