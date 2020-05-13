package com.curso.JWTAuthenticationRest.services;

import com.curso.JWTAuthenticationRest.constants.Constants;
import com.curso.JWTAuthenticationRest.exception.NotHavingSufficentBalance;
import com.curso.JWTAuthenticationRest.model.Account;
import com.curso.JWTAuthenticationRest.model.Transaction_History;
import com.curso.JWTAuthenticationRest.repositories.AccountRepository;
import com.curso.JWTAuthenticationRest.repositories.TransactionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.curso.JWTAuthenticationRest.constants.Constants.*;

@Service
public class TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public void setBalanceOfUser(String logged_in_user,String type,Double balance) throws NotHavingSufficentBalance {
        Account account_of_user=accountRepository.findByUsername(logged_in_user);
        if (account_of_user==null){
            account_of_user=new Account(logged_in_user);
            accountRepository.save(account_of_user);
        }
        if (type.equals(WITHDRAW)){
            if ( account_of_user.getAmount() < balance){
                throw new NotHavingSufficentBalance(BALANCEMESSAGE);
            }else{
                account_of_user.depositAmount(-balance);
            }
        }else if (type.equals(DEPOSIT)){
            account_of_user.depositAmount(balance);
        }
    }

    public Double getBalance(String username){
        Account account_of_user=accountRepository.findByUsername(username);
        return account_of_user.getAmount();
    }

    public void create_txn(String logged_in_user, Double balance, String comment, String txn_type){
        Transaction_History th= new Transaction_History(logged_in_user,balance,comment,txn_type);
        transactionRepository.save(th);
    }

    public List<Transaction_History> getHistory(String username){
        return transactionRepository.findByUsername(username);
    }

    public List<Transaction_History> getAllTheTransaction(String username){
        return transactionRepository.findByUsernameAndTxnTypeIsNotContaining(username,VIEW);
    }

    public List<Transaction_History> getAllTheViewTransaction(String username){
        return transactionRepository.findByUsernameAndTxnTypeIsContaining(username, VIEW);
    }

    public ModelAndView getModelView(Transaction_History tnx,String logged_in_user){

        ModelAndView mv=new ModelAndView();

        if (tnx.getTxnType().equals(Constants.VIEW)){

            Double amount=getBalance(logged_in_user);   // get the total balance in the logged in user account

            List<Transaction_History> list_of_txn=getAllTheViewTransaction(logged_in_user); //get all the view transaction

            mv.setViewName(BALANCE);
            mv.addObject(VAL,amount);
            mv.addObject(DATA,list_of_txn);

            return mv;
        }else{
            mv.setViewName(SUCESS);
            return mv;
        }
    }

    public ModelAndView getView(String viewName,List<Transaction_History> list_of_txn){
        ModelAndView mv=new ModelAndView();
        mv.setViewName(viewName);
        mv.addObject(DATA, list_of_txn);
        return mv;
    }
}
