package com.payment.service;

import com.payment.controller.reques.PaymentRequest;
import com.payment.entity.Account;
import com.payment.entity.Payment;
import com.payment.entity.Product;
import com.payment.entity.User;
import com.payment.repository.AccountRepository;
import com.payment.repository.PaymentRepository;
import com.payment.repository.ProductRepository;
import com.payment.repository.UserRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class PaymentServiceTest {

    public static final int USER_ID = 1;
    public static final int ACCOUNT_ID = 1;
    public static final int PRODUCT_ID_1 = 1;
    @Resource
    private PaymentRepository paymentRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private AccountRepository accountRepository;

    @Resource
    private ProductRepository productRepository;

    private PaymentService paymentService = mock(PaymentService.class);

    @Test
    void shouldBuildPaymentOf1Product() {

        Product product1 = productRepository.findById(PRODUCT_ID_1).orElseThrow(()-> new RuntimeException());
        List<Product> products = new ArrayList<Product>(){};
        products.add(product1);
        BigDecimal totalPrice = product1.getPrice();
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setProducts(products);
        when(paymentService.createPayment(USER_ID, ACCOUNT_ID, paymentRequest)).thenReturn(buildPaymenyt(products, totalPrice));

        Integer actual = paymentService.createPayment(USER_ID, ACCOUNT_ID, paymentRequest);
        Integer expected = buildPaymenyt(products, totalPrice);

        assertEquals(expected, actual);
    }

    private Integer buildPaymenyt(List<Product> products, BigDecimal totalPrice) {
        User user = userRepository.findById(USER_ID).orElseThrow(()-> new RuntimeException());
        Account account = accountRepository.findById(ACCOUNT_ID).orElseThrow(()-> new RuntimeException());
        Payment payment = new Payment();
        payment.setProducts(products);
        payment.setAccount(account);
        payment.setTotalPrice(totalPrice);
        payment.setUser(user);

        return payment.getId();
    }

}