package com.payment.service;

import com.payment.controller.reques.PaymentRequest;
import com.payment.dao.AccountDAO;
import com.payment.dao.PaymentDAO;
import com.payment.dao.ProductDAO;
import com.payment.dao.UserDAO;
import com.payment.entity.Account;
import com.payment.entity.Payment;
import com.payment.entity.Product;
import com.payment.entity.User;
import com.payment.repository.AccountRepository;
import com.payment.repository.PaymentRepository;
import com.payment.repository.ProductRepository;
import com.payment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private UserDAO userAccessDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private PaymentDAO paymentDAO;

    public String buildPayment(Integer userId, Integer accountId, PaymentRequest paymentRequest) {
        User user = userAccessDAO.getUserByID(userId);
        Account account = accountDAO.getAccount(accountId);
        List<Product> products = getProducts(paymentRequest);
        BigDecimal totalPrice = getTotalPrice(products);
        Payment payment = buildPayment(user, account, products, totalPrice);
        byte[] paymentInBytes = String.valueOf(payment.getId()).getBytes();
        return getPaymentString(paymentInBytes);
    }

    private static BigDecimal getTotalPrice(List<Product> products) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(Product p : products) {
            totalPrice = totalPrice.add(p.getPrice());
        }
        return totalPrice;
    }

    private Payment buildPayment(User user, Account account, List<Product> products, BigDecimal totalPrice) {
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setAccount(account);
        payment.setProducts(products);
        payment.setTotalPrice(totalPrice);
        payment = paymentDAO.savePayment(payment);
        return payment;
    }

    private static String getPaymentString(byte[] paymentInBytes) {
        return new StringBuilder("http://localhost:8080/")
                .append("/api/v1/payments/")
                .append(Base64.getEncoder().encodeToString(paymentInBytes))
                .toString();
    }

    private List<Product> getProducts(PaymentRequest paymentRequest) {
        List<Product> products = paymentRequest
                .getProducts()
                .stream()
                .map(p -> productDAO.getProduct(p))
                .collect(Collectors.toList());
        return products;
    }

    public Payment buildPayment(String paymentHash) {
        String decoded = new String(Base64.getDecoder().decode(paymentHash));
        Payment payment = paymentDAO.getPaymentByID(decoded);
        payment.setConfirmed(true);
        return payment;
    }
}
