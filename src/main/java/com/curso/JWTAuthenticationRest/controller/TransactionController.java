package com.curso.JWTAuthenticationRest.controller;

import com.curso.JWTAuthenticationRest.exception.NotHavingSufficentBalance;
import com.curso.JWTAuthenticationRest.model.Transaction_History;
import com.curso.JWTAuthenticationRest.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static com.curso.JWTAuthenticationRest.constants.Constants.DATA;
import static com.curso.JWTAuthenticationRest.constants.Constants.TransactionHistory;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/type")
    @ResponseBody
    public ModelAndView home(
            HttpServletRequest httpServletRequest,
             Transaction_History tnx
    ) throws NotHavingSufficentBalance {

        String logged_in_user=httpServletRequest.getRemoteUser();    // to find out the current user

       // transactionService.setBalanceOfUser(logged_in_user, tnx.getTxnType(),tnx.getAmount());  // get the account of current user

       // transactionService.create_txn(logged_in_user, tnx.getAmount(),tnx.getComment(),tnx.getTxnType()); // add the transaction record .

        transactionService.withdraw_and_update_transaction(logged_in_user, tnx.getTxnType(), tnx.getAmount(), tnx.getComment());

        ModelAndView mv=transactionService.getModelView(tnx, logged_in_user); // holder for model and view

        return mv;
    }

    @RequestMapping("/history")
    public ModelAndView history(HttpServletRequest httpServletRequest){

        // to find the current user
        String logged_in_user=httpServletRequest.getRemoteUser();

        // get all the transaction of the particular user
        List<Transaction_History> list_of_txn=transactionService.getHistory(logged_in_user);

        ModelAndView mv= transactionService.getView(TransactionHistory);
        mv.addObject(DATA, list_of_txn);
        return mv;
    }

    @RequestMapping("/showtransaction")
    public ModelAndView showTransaction(HttpServletRequest httpServletRequest){

        String logged_in_user=httpServletRequest.getRemoteUser(); // to find the current user

        List<Transaction_History> list_of_txn=transactionService.getAllTheTransaction(logged_in_user); // get all the txn

        ModelAndView mv= transactionService.getView(TransactionHistory);
        mv.addObject(DATA, list_of_txn);
        return mv;
    }
}
