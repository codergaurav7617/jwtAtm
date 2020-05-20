package com.curso.JWTAuthenticationRest.skeleton;

import com.curso.JWTAuthenticationRest.exception.NotHavingSufficentBalance;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ATMTest {

    RestTemplate restTemplate = new RestTemplate();

    private final String username = UUID.randomUUID().toString();
    private final String password = UUID.randomUUID().toString();
    private String token;

    @Given("Sign up")
    public void Sign_up() throws Throwable {
        System.out.println(restTemplate.postForEntity("http://localhost:8051/user/register?user=" + username +
                "&&password=" + password, "", String.class).getBody());
    }

    @Given("Get Token")
    public void Get_Token() throws Throwable {
        String response = restTemplate.postForEntity("http://localhost:8051/token?user=" + username +
                "&&password=" + password, "", String.class).getBody();
        System.out.println(response);
        token = response.substring(2, response.length()-2);
        System.out.println(token);
    }

    @Then("View Balance for new user")
    public void View_Balance() {
        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + token);

        HttpEntity entity = new HttpEntity(headers);

        String response = restTemplate.postForEntity("http://localhost:8051/transaction/type?username=" + username +
                "&txnType=view", entity, String.class).getBody();
        System.out.println(response);
        Assert.assertTrue(response.contains("<span>0.0</span>"));
    }

    @Then("deposit and withdraw parellel")
    public void deposit_and_withdraw() throws ExecutionException, InterruptedException {
        for (int j=0;j<1000;j++){
            CompletableFuture<String>[] allFuturesDeposit = new CompletableFuture[3];
            for (int i = 0; i < 3; i++) {
                allFuturesDeposit[i] = CompletableFuture.supplyAsync(() -> {
                    try {
                        return deposit_Balance();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
            }

            CompletableFuture.allOf(allFuturesDeposit).join(); //This will join all threads
            for (CompletableFuture<String> responseCompletableFuture : allFuturesDeposit) {
                System.out.println(responseCompletableFuture.get());
            }

            CompletableFuture<String>[] allFuturesWithdraw = new CompletableFuture[4];

            for (int i = 0; i < 4; i++) {
                allFuturesWithdraw[i] = CompletableFuture.supplyAsync(() -> {
                    return withdraw_balance();
                });
            }

            CompletableFuture.allOf(allFuturesWithdraw).join(); //This will join all threads
            for (CompletableFuture<String> responseCompletableFuture : allFuturesWithdraw) {
                System.out.println(responseCompletableFuture.get());
            }
        }
    }

    @Then("deposit Balance for new user")
    public String deposit_Balance() throws ExecutionException, InterruptedException {

        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + token);

        HttpEntity entity = new HttpEntity(headers);
        String response = restTemplate.postForEntity("http://localhost:8051/transaction/type?username=" + username +
                "&txnType=Deposit"+ "&amount=50", entity, String.class).getBody();
         return response;
    }

    @Then("withdraw balance from the existing user")
    public String withdraw_balance(){

        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + token);

        HttpEntity entity = new HttpEntity(headers);
        String response = restTemplate.postForEntity("http://localhost:8051/transaction/type?username=" + username +
                "&txnType=withdraw"+ "&amount=50", entity, String.class).getBody();
        Assert.assertTrue(response.contains(" <title>Transaction Sucessful</title>"));
        return response;
    }

    @Then("withdraw balance from the new user")
    public void withdraw_balance_for_new_user() throws NotHavingSufficentBalance {

        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + token);

        HttpEntity entity = new HttpEntity(headers);

        try {
            String response = restTemplate.postForEntity("http://localhost:8051/transaction/type?username=" + username +
                    "&txnType=withdraw" + "&amount=50", entity, String.class).getBody();
            System.out.println(response);
        }catch (HttpStatusCodeException ex){
            String response = ex.getResponseBodyAsString();
            Assert.assertTrue(response.contains("Not Having Sufficient balance"));
        }
    }
}