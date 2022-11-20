package com.payment.service;

import com.payment.entity.Account;
import com.payment.entity.User;
import com.payment.exceptions.AccountException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    public static final int ACCOUNT_ID = 1;
    private AccountService accountService = mock(AccountService.class);

    @Test
    void shouldGetAccountByID() throws Exception{
        var account = buildAccount();
        when(accountService.getAccountById(ACCOUNT_ID)).thenReturn(account);
        var actual = accountService.getAccountById(ACCOUNT_ID);

        assertEquals(account, actual);
    }

    @Test
    void shouldThrowException() throws Exception {
        String errorMessage = "Account not found";
        when(accountService.getAccountById(ACCOUNT_ID)).thenThrow(new AccountException(errorMessage));
        try { accountService.getAccountById(ACCOUNT_ID); }
        catch (Exception exception) {
            assertEquals(errorMessage, exception.getMessage());
        }

    }

    private Account buildAccount () {
        var account = new Account();
        var user = new User();
        account.setUser(user);
        account.setId(ACCOUNT_ID);
        account.setName("John Doe");
        account.setIban("something");
        return account;
    }
}