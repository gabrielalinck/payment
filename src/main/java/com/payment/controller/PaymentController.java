package com.payment.controller;

import java.util.List;

import com.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.controller.reques.PaymentRequest;
import com.payment.entity.Payment;
import com.payment.repository.AccountRepository;
import com.payment.repository.PaymentRepository;
import com.payment.repository.ProductRepository;
import com.payment.repository.UserRepository;

@RestController
@RequestMapping("api/payments")
public class PaymentController {
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private PaymentService paymentService;
	
	@GetMapping
	public List<Payment> getAll() {
		return (List<Payment>) paymentRepository.findAll();
	}
	
	@PostMapping("/{userId}/{accountId}")
	public String createPayment(@PathVariable Integer userId,@PathVariable Integer accountId, @RequestBody PaymentRequest paymentRequest) {
		return paymentService.buildPayment(userId, accountId, paymentRequest);
	}

	@PostMapping("/{paymentHash}")
	public Payment confirmPayment(@PathVariable String paymentHash) {
		return paymentService.buildPayment(paymentHash);
	}



}
