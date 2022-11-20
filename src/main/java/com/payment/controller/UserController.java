package com.payment.controller;

import com.payment.entity.User;
import com.payment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
