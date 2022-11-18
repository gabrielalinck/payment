package com.payment.dao;

import com.payment.entity.User;
import com.payment.exceptions.UserException;
import com.payment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserDAO {

    @Autowired
    private UserRepository userRepository;

    public User getUserByID(Integer userId) throws UserException{
        User user = userRepository.findById(userId).orElseThrow(()-> new UserException("User not found"));
        return user;
    }
}
