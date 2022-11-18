package com.payment.controller;

import com.payment.controller.reques.PaymentRequest;
import com.payment.entity.Account;
import com.payment.entity.Product;
import com.payment.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class PaymentControllerTest {
    @Autowired
    private PaymentController controller;

    @Test
    void shouldDoSomething() {
        int someVariable = 0;
        assertEquals(someVariable, 0);
    }

    /*@PostMapping("/{userId}/{accountId}")
    public String createPayment(@PathVariable Integer userId, @PathVariable Integer accountId, @RequestBody PaymentRequest paymentRequest) {
        return paymentService.getPayment(userId, accountId, paymentRequest);
    }*/

//    @Test
//    void shouldCreateAPayment() {
//        Integer userId = 1;
//        Integer accountId = 1;
//        PaymentRequest paymentRequest = new PaymentRequest();
//        List<Product> products = new ArrayList<Product>() {};
//        Product product1 = getProduct();
//        products.add(product1);
//        paymentRequest.setProducts(products);
//        String actual = controller.createPayment(userId, accountId, paymentRequest);
//        assert actual.isEmpty();
//
//    }

    private static Product getProduct() {
        Product product1 = new Product();
        Account account = getAccount();
        product1.setId(1);
        product1.setName("Product1");
        product1.setPrice(BigDecimal.valueOf(10.0));
        product1.setAccount(account);
        return product1;
    }

    private static Account getAccount() {
        Account account = new Account();
        User user = getUser();
        account.setIban("something");
        account.setId(1);
        account.setUser(user);
        account.setName("something");
        return account;
    }

    private static User getUser() {
        User user = new User();
        user.setId(1);
        user.setName("name");
        user.setEmail("something@acme.com");
        return user;
    }
}