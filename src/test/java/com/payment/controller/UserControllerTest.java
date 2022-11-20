package com.payment.controller;

import com.payment.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
@SpringBootTest
class UserControllerTest {

    public static final int USER_ID = 1;
    public static final int WRONG_USER_ID = 1000000000;
    public static final String USER_NOT_FOUND = "User not found";
    @Autowired
    private UserController controller;

    @Test
    void shouldGetAllUsers() throws Exception {
        List<User> users = controller.getUsers();
        assertFalse(users.isEmpty());
    }

    @Test
    void shouldGetUserById() throws Exception {
        User actual = controller.getUserById(USER_ID);
        assertEquals(actual.getId(), USER_ID);
    }

    @Test
    void shouldThrowExceptionWhenUserIdIsWrong() throws Exception {
        try { controller.getUserById(WRONG_USER_ID); }
        catch (Exception exception) {assertEquals(USER_NOT_FOUND, exception.getMessage());}
    }

    @Test
    void shouldCreateNewUser() throws Exception {
        User user = new User();
        user.setName("Jack Doe");
        user.setEmail("jack@acme.com");
        User actual = controller.createUser(user);
        assertEquals(actual, user);
    }


}