package com.payment.dao;

import com.payment.entity.Account;
import com.payment.exceptions.AccountException;
import com.payment.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class AccountDAO {

    @Autowired
    private AccountRepository accountRepository;

    public Account getAccount(Integer accountId) throws AccountException {
        Account account = accountRepository.findById(accountId).orElseThrow(()-> new AccountException("Account not found"));
        return account;
    }
}
