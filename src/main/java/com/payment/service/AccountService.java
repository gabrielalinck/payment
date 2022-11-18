package com.payment.service;

import com.payment.dao.AccountDAO;
import com.payment.dao.UserDAO;
import com.payment.entity.Account;
import com.payment.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountDAO dao;

    public AccountService(AccountDAO dao) {this.dao = dao;}

    public Account getAccountById(Integer accountId) throws Exception{
        return dao.getAccount(accountId);
    }
}
