package com.payment.controller;

import java.util.Base64;
import java.util.List;

import com.payment.exceptions.PaymentException;
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
	public List<Payment> getAll() throws Exception {
		return paymentService.getPayments();
	}

	@PostMapping("/{userId}/{accountId}")
	public String createPayment(
			@PathVariable Integer userId,
			@PathVariable Integer accountId,
			@RequestBody PaymentRequest paymentRequest
	) throws PaymentException {
		Integer paymentId = paymentService.createPayment(userId, accountId, paymentRequest);
		byte[] paymentInBytes = String.valueOf(paymentId).getBytes();
		return getPaymentString(paymentInBytes);
	}

	@PostMapping("/{paymentHash}")
	public Payment confirmPayment(@PathVariable String paymentHash) throws Exception {
		return paymentService.confirmPayment(paymentHash);
	}

	private static String getPaymentString(byte[] paymentInBytes) {
		return new StringBuilder("http://localhost:8080/")
				.append("api/v1/payments/")
				.append(Base64.getEncoder().encodeToString(paymentInBytes))
				.toString();
	}


}
