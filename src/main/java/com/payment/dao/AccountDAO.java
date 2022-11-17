package com.payment.dao;

import com.payment.entity.Account;
import com.payment.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountDAO {

    @Autowired
    private AccountRepository accountRepository;

    public Account getAccount(Integer accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(()-> new RuntimeException());
        return account;
    }
}
