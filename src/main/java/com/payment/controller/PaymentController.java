package com.payment.controller;

import com.payment.controller.reques.PaymentRequest;
import com.payment.entity.Payment;
import com.payment.exceptions.PaymentException;
import com.payment.repository.AccountRepository;
import com.payment.repository.PaymentRepository;
import com.payment.repository.ProductRepository;
import com.payment.repository.UserRepository;
import com.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
	public List<Payment> getAll() throws Exception {
		return paymentService.getPayments();
	}

	@PostMapping("/{userId}/{accountId}")
	public String createPayment(
			@PathVariable Integer userId,
			@PathVariable Integer accountId,
			@RequestBody PaymentRequest paymentRequest
	) throws PaymentException {
		return paymentService.createPayment(userId, accountId, paymentRequest);
	}

	@PostMapping("/{paymentHash}")
	public Payment confirmPayment(@PathVariable String paymentHash) throws Exception {
		return paymentService.confirmPayment(paymentHash);
	}




}
