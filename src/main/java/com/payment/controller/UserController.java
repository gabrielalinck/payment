package com.payment.controller;

import java.util.List;

import com.payment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.entity.User;

@RestController
@RequestMapping("api/users")
public class UserController {

	@Autowired
	private UserService service;
	
	@GetMapping
	public List<User> getUsers() throws Exception {
		return service.getUserList();
	}

	@GetMapping("/{userId}")
	public User getUserById(@PathVariable Integer userId) throws Exception{
		return service.getUserById(userId);
	}
	
	@PostMapping
	public User createUser(@RequestBody User user) throws Exception {
		return service.saveUser(user);
	}



}
