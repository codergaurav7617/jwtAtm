package com.curso.JWTAuthenticationRest.exception;

public class NotHavingSufficentBalance extends Exception {
    private static final long serialVersionUID = -47805079010368L;
    public NotHavingSufficentBalance(){
        super();
    }
    public NotHavingSufficentBalance(final String message){
        super(message);
    }
}
