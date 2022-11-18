package com.payment.service;

import com.payment.dao.UserDAO;
import com.payment.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class UserService {

    @Autowired
    private UserDAO dao;

    public UserService(UserDAO dao) {this.dao = dao;}

    public User getUserById(Integer userId) {
        return dao.getUserByID(userId);
    }

}
