package com.curso.JWTAuthenticationRest.config;

import com.curso.JWTAuthenticationRest.model.Account;
import com.curso.JWTAuthenticationRest.model.Transaction_History;
import com.curso.JWTAuthenticationRest.repositories.AccountRepository;
import com.curso.JWTAuthenticationRest.repositories.TransactionRepository;
import com.curso.JWTAuthenticationRest.services.TransactionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@Configuration
public class H2TestProfile {

    @Bean
    public TransactionService getTransactionBean(){
        return new TransactionService(getAccountBean(), getTransBean());
    }

    @Bean
    public TransactionRepository getTransBean(){
        return new TransactionRepository() {
            @Override
            public List<Transaction_History> findByUsername(String username) {
                return null;
            }

            @Override
            public List<Transaction_History> findByUsernameAndTxnTypeIsNotContaining(String username, String str) {
                return null;
            }

            @Override
            public List<Transaction_History> findByUsernameAndTxnTypeIsContaining(String username, String str) {
                return null;
            }

            @Override
            public List<Transaction_History> findAll() {
                return null;
            }

            @Override
            public List<Transaction_History> findAll(Sort sort) {
                return null;
            }

            @Override
            public List<Transaction_History> findAllById(Iterable<Integer> iterable) {
                return null;
            }

            @Override
            public <S extends Transaction_History> List<S> saveAll(Iterable<S> iterable) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends Transaction_History> S saveAndFlush(S s) {
                return null;
            }

            @Override
            public void deleteInBatch(Iterable<Transaction_History> iterable) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public Transaction_History getOne(Integer integer) {
                return null;
            }

            @Override
            public <S extends Transaction_History> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends Transaction_History> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public Page<Transaction_History> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Transaction_History> S save(S s) {
                return null;
            }

            @Override
            public Optional<Transaction_History> findById(Integer integer) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Integer integer) {
                return false;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Integer integer) {

            }

            @Override
            public void delete(Transaction_History transaction_history) {

            }

            @Override
            public void deleteAll(Iterable<? extends Transaction_History> iterable) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public <S extends Transaction_History> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends Transaction_History> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Transaction_History> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends Transaction_History> boolean exists(Example<S> example) {
                return false;
            }
        };
    }

    @Bean
    public AccountRepository getAccountBean() {
        System.out.println("A");
        return new AccountRepository() {
            @Override
            public Account findByUsername(String username) {
                return null;
            }

            @Override
            public int numberOfRRowUpdateForWithdrawal(Double balance, String username) {
                return 0;
            }

            @Override
            public int numberOfRRowUpdateForDeposit(Double balance, String username) {
                return 0;
            }

            @Override
            public List<Account> findAll() {
                return null;
            }

            @Override
            public List<Account> findAll(Sort sort) {
                return null;
            }

            @Override
            public List<Account> findAllById(Iterable<Integer> iterable) {
                return null;
            }

            @Override
            public <S extends Account> List<S> saveAll(Iterable<S> iterable) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends Account> S saveAndFlush(S s) {
                return null;
            }

            @Override
            public void deleteInBatch(Iterable<Account> iterable) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public Account getOne(Integer integer) {
                return null;
            }

            @Override
            public <S extends Account> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends Account> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public Page<Account> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Account> S save(S s) {
                return null;
            }

            @Override
            public Optional<Account> findById(Integer integer) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Integer integer) {
                return false;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Integer integer) {

            }

            @Override
            public void delete(Account account) {

            }

            @Override
            public void deleteAll(Iterable<? extends Account> iterable) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public <S extends Account> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends Account> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Account> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends Account> boolean exists(Example<S> example) {
                return false;
            }
        };
    }
}
