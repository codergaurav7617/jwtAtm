package com.curso.JWTAuthenticationRest.services;

import com.curso.JWTAuthenticationRest.constants.Constants;
import com.curso.JWTAuthenticationRest.exception.NotHavingSufficentBalance;
import com.curso.JWTAuthenticationRest.model.Account;
import com.curso.JWTAuthenticationRest.model.Transaction_History;
import com.curso.JWTAuthenticationRest.repositories.AccountRepository;
import com.curso.JWTAuthenticationRest.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.*;
import org.springframework.retry.annotation.Backoff;

import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import static com.curso.JWTAuthenticationRest.constants.Constants.*;

@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(AccountRepository accountRepository, TransactionRepository transactionRepository) {

        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
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

    public ModelAndView getView(String viewName){
        ModelAndView mv=new ModelAndView();
        mv.setViewName(viewName);
        return mv;
    }

    public ModelAndView getModelView(Transaction_History tnx,String logged_in_user){

        if (tnx.getTxnType().equals(Constants.VIEW)){

            Double amount=getBalance(logged_in_user);   // get the total balance in the logged in user account

            List<Transaction_History> list_of_txn=getAllTheViewTransaction(logged_in_user); //get all the view transaction

            ModelAndView mv=getView(BALANCE);
            mv.addObject(VAL,amount);
            mv.addObject(DATA,list_of_txn);
            return mv;
        }else{
            return getView(SUCESS);
        }
    }

    @Retryable(maxAttempts = 4, backoff = @Backoff(delay = 500, multiplier = 2), include = { CannotAcquireLockException.class,
            QueryTimeoutException.class,
            ConcurrencyFailureException.class, DataAccessResourceFailureException.class,RuntimeException.class})
    public  void setBalanceOfUser(String logged_in_user,String type,Double balance) throws NotHavingSufficentBalance{
        System.out.println("Gaurav");
        if (type.equals(WITHDRAW)){
            int num_row_affeted = accountRepository.numberOfRRowUpdateForWithdrawal(balance, logged_in_user);
            if (num_row_affeted==0){
                throw new NotHavingSufficentBalance("Not Having Sufficient balance");
            }
        }else if (type.equals(DEPOSIT)){
            accountRepository.numberOfRRowUpdateForDeposit(balance, logged_in_user);
        }
    }

    @Transactional
    @Retryable(maxAttempts = 4, backoff = @Backoff(delay = 500, multiplier = 2), include = { CannotAcquireLockException.class,
            QueryTimeoutException.class,
            ConcurrencyFailureException.class, DataAccessResourceFailureException.class,RuntimeException.class})
    public void withdraw_and_update_transaction(String logged_in_user,String type,Double balance, String comment)
            throws NotHavingSufficentBalance{
        System.out.println("Calling the withdrwa api");
        if (type.equals(WITHDRAW)){
            int num_row_affeted = accountRepository.numberOfRRowUpdateForWithdrawal(balance, logged_in_user);
            if (num_row_affeted==0){
                throw new NotHavingSufficentBalance("Not Having Sufficient balance");
            }
        }else if (type.equals(DEPOSIT)){
            System.out.println(accountRepository);
            int ac=accountRepository.numberOfRRowUpdateForDeposit(balance, logged_in_user);
            System.out.println("gaurav");
            System.out.println(ac);
        }
            Transaction_History th = new Transaction_History(logged_in_user, balance, comment, type);
            System.out.println(th);
            transactionRepository.save(th);
    }

}
