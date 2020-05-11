package com.curso.JWTAuthenticationRest.controller;

import com.curso.JWTAuthenticationRest.exception.NotHavingSufficentBalance;
import com.curso.JWTAuthenticationRest.model.Transaction_History;
import com.curso.JWTAuthenticationRest.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
        String logged_in_user=transactionService.getUser(httpServletRequest);    // to find out the current user

        transactionService.setBalanceOfUser(logged_in_user, tnx.getTxnType(),tnx.getAmount());  // get the account of current user

        transactionService.create_txn(logged_in_user, tnx.getAmount(),tnx.getComment(),tnx.getTxnType()); // add the transaction record .

        ModelAndView mv=transactionService.getModelView(tnx, logged_in_user); // holder for model and view

        return mv;
    }

    @RequestMapping("/history")
    public ModelAndView history(HttpServletRequest httpServletRequest){

        // to find the current user
        String logged_in_user=transactionService.getUser(httpServletRequest);

        // get all the transaction of the particular user
        List<Transaction_History> list_of_txn=transactionService.getHistory(logged_in_user);

        // holder for model and view
        ModelAndView mv=new ModelAndView();
        mv.setViewName("Transaction_History");
        mv.addObject("data",list_of_txn);
        return mv;
    }

    @ResponseBody
    @RequestMapping("/showtransaction")
    public ModelAndView showTransaction(HttpServletRequest httpServletRequest){
        String logged_in_user=transactionService.getUser(httpServletRequest); // to find the current user

        List<Transaction_History> list_of_txn=transactionService.getAllTheTransaction(logged_in_user); // get all the txn

        ModelAndView mv=new ModelAndView(); // holder for model and view
        mv.setViewName("Transaction_History");
        mv.addObject("data",list_of_txn);
        return mv;
    }
}
