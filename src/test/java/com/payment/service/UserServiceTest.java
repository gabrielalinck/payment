

package com.payment.service;

import com.payment.entity.User;

import com.payment.exceptions.UserException;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    public static final int USER_ID = 1;
    private UserService userService = mock(UserService.class);

    @Test
    void shouldGetUserByID() throws Exception {
        var user = buildUser();
        when(userService.getUserById(USER_ID)).thenReturn(user);
        var actual = userService.getUserById(USER_ID);

        assertEquals(user, actual);
    }

    @Test
    void shouldThrowExceptionWhenGetUserById() throws Exception {
        String errorMessage = "Account not found";
        when(userService.getUserById(USER_ID)).thenThrow(new UserException(errorMessage));
        try { userService.getUserById(USER_ID); }
        catch (Exception exception) {
            assertEquals(errorMessage, exception.getMessage());
        }

    }

    @Test
    void shouldGetAllUsers() throws Exception {
        var user = buildUser();
        List<User> users = new ArrayList<User>(){};
        users.add(user);
        when(userService.getUserList()).thenReturn(users);
        var actual = userService.getUserList();

        assertEquals(users, actual);
    }

    @Test
    void shouldThrowExceptionWhenGetListOfUsers() throws Exception {
        String errorMessage = "Users list not found";
        when(userService.getUserList()).thenThrow(new UserException(errorMessage));
        try { userService.getUserList(); }
        catch (Exception exception) {
            assertEquals(errorMessage, exception.getMessage());
        }

    }
    @Test
    void shouldSaveUser() throws Exception {
        User user = buildUser();
        when(userService.saveUser(user)).thenReturn(user);
        User actual = userService.saveUser(user);

        assertEquals(actual, user);
    }

    @Test
    void shouldNotSaveUser() throws Exception {
        User user = buildUser();
        String errorMessage = "User not saved";
        when(userService.saveUser(user)).thenThrow(new UserException(errorMessage));

        try { userService.saveUser(user); }
        catch (Exception exception) { assertEquals(errorMessage, exception.getMessage()); }
    }

    private User buildUser() {
        var user = new User();
        user.setEmail("something@emai.com");
        user.setId(USER_ID);
        user.setName("John Doe");
        return user;
    }
}