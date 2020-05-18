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
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
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
            ConcurrencyFailureException.class, DataAccessResourceFailureException.class})

    public  void setBalanceOfUser(String logged_in_user,String type,Double balance) throws NotHavingSufficentBalance{
        System.out.println("abc");
        if (type.equals(WITHDRAW)){
            int num_row_affeted = accountRepository.numberOfRRowUpdateForWithdrawal(balance, logged_in_user);
            if (num_row_affeted==0){
                throw new NotHavingSufficentBalance("Not Having Sufficient balance");
            }
        }else if (type.equals(DEPOSIT)){
            accountRepository.numberOfRRowUpdateForDeposit(balance, logged_in_user);
        }
        throw new QueryTimeoutException("time out");
    }

    @Recover
    public void recover(Exception e,String logged_in_user,String type,Double balance) throws Exception{
        throw new Exception("Insufficent balance");
    }

    @Recover
    public void recover(CannotAcquireLockException ex,String logged_in_user,String type,Double balance) throws CannotAcquireLockException {
        throw new CannotAcquireLockException("cannot aquire the lock");
    }


    @Recover
    public void recover(QueryTimeoutException ex,String logged_in_user,String type,Double balance) throws QueryTimeoutException {
        throw new QueryTimeoutException("Querry time out exception ocurred");
    }

    @Recover
    public void recover(ConcurrencyFailureException ex,String logged_in_user,String type,Double balance) throws ConcurrencyFailureException {
        throw new ConcurrencyFailureException("Concurrency failure exception");
    }

    @Recover
    public void recover(DataAccessResourceFailureException ex,String logged_in_user,String type,Double balance) throws DataAccessResourceFailureException {
        throw new DataAccessResourceFailureException("Data Access resource failure exception");
    }
}
