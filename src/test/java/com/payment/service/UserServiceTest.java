package com.payment.service;

import com.payment.entity.User;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private UserService userService = mock(UserService.class);

    @Test
    void shouldGetUserByID() {
        int userID = 1;
        var user = buildUser(userID);
        when(userService.getUserById(userID)).thenReturn(user);
        var actual = userService.getUserById(userID);

        assertEquals(user, actual);
    }

    private User buildUser(Integer userId) {
        var user = new User();
        user.setEmail("something@emai.com");
        user.setId(userId);
        user.setName("John Doe");
        return user;
    }
}