

package com.payment.service;

import com.payment.entity.User;

import com.payment.exceptions.UserException;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    public static final int USER_ID = 1;
    private UserService userService = mock(UserService.class);

    @Test
    void shouldGetUserByID() throws Exception {
        var user = buildUser(USER_ID);
        when(userService.getUserById(USER_ID)).thenReturn(user);
        var actual = userService.getUserById(USER_ID);

        assertEquals(user, actual);
    }

    @Test
    void shouldThrowException() throws Exception {
        String errorMessage = "Account not found";
        when(userService.getUserById(USER_ID)).thenThrow(new UserException(errorMessage));
        try { userService.getUserById(USER_ID); }
        catch (Exception exception) {
            assertEquals(errorMessage, exception.getMessage());
        }

    }

    private User buildUser(Integer userId) {
        var user = new User();
        user.setEmail("something@emai.com");
        user.setId(userId);
        user.setName("John Doe");
        return user;
    }
}