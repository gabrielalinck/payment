package com.payment.service;

import com.payment.dao.UserDAO;
import com.payment.entity.User;
import com.payment.exceptions.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class UserService {

    @Autowired
    private UserDAO dao;

    public UserService(UserDAO dao) {this.dao = dao;}

    public User getUserById(Integer userId) throws UserException {
        try { return dao.getUserByID(userId); }
        catch (Exception exception) { throw new UserException(exception.getMessage()); }
    }

}
