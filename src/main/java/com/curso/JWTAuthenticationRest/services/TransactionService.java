package com.curso.JWTAuthenticationRest.services;

import com.curso.JWTAuthenticationRest.constants.Constants;
import com.curso.JWTAuthenticationRest.exception.NotHavingSufficentBalance;
import com.curso.JWTAuthenticationRest.model.Account;
import com.curso.JWTAuthenticationRest.model.Transaction_History;
import com.curso.JWTAuthenticationRest.repositories.AccountRepository;
import com.curso.JWTAuthenticationRest.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
