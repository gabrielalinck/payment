package com.payment.dao;

import com.payment.entity.Payment;
import com.payment.exceptions.PaymentException;
import com.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;

@Repository
@Transactional
public class PaymentDAO {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment getPaymentByID(String decoded) throws PaymentException {
        return paymentRepository.findById(Integer.parseInt(decoded))
                .orElseThrow(() -> new PaymentException("Couldn't find a payment with this ID"));
    }

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public List<Payment> getPayments() {
        return (List<Payment>) paymentRepository.findAll();
    }
}
