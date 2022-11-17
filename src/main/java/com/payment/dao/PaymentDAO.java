package com.payment.dao;

import com.payment.entity.Payment;
import com.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Base64;

public class PaymentDAO {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment getPaymentByID(String decoded) {
        return paymentRepository.findById(Integer.parseInt(decoded)).orElseThrow(() -> new RuntimeException());
    }

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }
}