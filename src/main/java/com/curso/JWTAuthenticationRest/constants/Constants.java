package com.curso.JWTAuthenticationRest.constants;

import org.springframework.security.web.csrf.CsrfFilter;

import java.text.SimpleDateFormat;

public class Constants {

    public final static String YOUR_SECRET = "your_secret";

    public final static String USER_ID = "userId";

    public final static String ROLE = "role";

    public final static String AUTHORIZATION_HEADER = "Authorization";

    public final static String BEARER_TOKEN = "Bearer ";

    public final static String WITHDRAW="withdraw";

    public final static String VIEW="view";

    public final static String DEPOSIT="Deposit";

    public final static SimpleDateFormat FORMATTER = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");

    public final static String ADMIN="Admin";

    public final static int ID=1;

    public final static String VAL="val";

    public final static String BALANCE="balance";

    public final static String DATA="data";

    public final static String SUCESS="sucess";

    public final static String TRANSACTIONTYPE="Transaction_Type";

    public final static String TransactionHistory="Transaction_History";

    public final static String WITHDRAWHOME="withdrawHome";

    public final static String DEPOSITHOME="depositHome";

    public final static String BALANCEMESSAGE="please enter the correct ammount";

    public final static String TOKEN ="token";
}
