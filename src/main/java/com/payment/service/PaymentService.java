package com.payment.service;

import com.payment.controller.reques.PaymentRequest;
import com.payment.dao.PaymentDAO;
import com.payment.entity.Account;
import com.payment.entity.Payment;
import com.payment.entity.Product;
import com.payment.entity.User;
import com.payment.exceptions.PaymentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentService {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PaymentDAO paymentDAO;

    public Integer createPayment(Integer userId, Integer accountId, PaymentRequest paymentRequest) throws PaymentException{
        try {
            User user = userService.getUserById(userId);
            Account account = accountService.getAccountById(accountId);
            List<Product> products = generateListOfProducts(paymentRequest);
            BigDecimal totalPrice = getTotalPrice(products);
            Payment payment = savePayment(user, account, products, totalPrice);
            return payment.getId();
        } catch (Exception exception) {
            throw new PaymentException(exception.getMessage());
        }
    }

    public Payment confirmPayment(String paymentHash) throws PaymentException {
        try {
            String decoded = new String(Base64.getDecoder().decode(paymentHash));
            Payment payment = paymentDAO.getPaymentByID(decoded);
            payment.setConfirmed(true);
            return payment;
        } catch(Exception exception) {
            throw new PaymentException(exception.getMessage());
        }
    }

    private static BigDecimal getTotalPrice(List<Product> products) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(Product p : products) {
            totalPrice = totalPrice.add(p.getPrice());
        }
        return totalPrice;
    }

    private Payment savePayment(User user, Account account, List<Product> products, BigDecimal totalPrice) throws PaymentException {
        try {
            Payment payment = new Payment();
            payment.setUser(user);
            payment.setAccount(account);
            payment.setProducts(products);
            payment.setTotalPrice(totalPrice);
            payment = paymentDAO.savePayment(payment);
            return payment;
        } catch (Exception exception) {
            throw new PaymentException("Payment couldn't be saved");
        }
    }

    private List<Product> generateListOfProducts(PaymentRequest paymentRequest) throws PaymentException {
        try {
            List<Product> products = paymentRequest
                    .getProducts()
                    .stream()
                    .map(p -> {
                        try {
                            return productService.getProductById(p.getId());
                        } catch (Exception exception) {
                            throw new RuntimeException(exception);
                        }
                    })
                    .collect(Collectors.toList());
            return products;
        } catch (Exception exception) {
            throw new PaymentException("Product not found");
        }
    }
}
