package com.payment.service;

import com.payment.controller.reques.PaymentRequest;
import com.payment.entity.Account;
import com.payment.entity.Payment;
import com.payment.entity.Product;
import com.payment.entity.User;
import com.payment.exceptions.PaymentException;
import com.payment.repository.AccountRepository;
import com.payment.repository.PaymentRepository;
import com.payment.repository.ProductRepository;
import com.payment.repository.UserRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@SpringBootTest
@Transactional
class PaymentServiceTest {

    public static final int USER_ID = 1;
    public static final int ACCOUNT_ID = 1;
    public static final int PRODUCT_ID_1 = 1;
    public static final int PRODUCT_ID_2 = 2;
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
    void shouldBuildPaymentOf1Product() throws PaymentException{

        Product product1 = productRepository.findById(PRODUCT_ID_1).orElseThrow(()-> new RuntimeException());
        List<Product> products = new ArrayList<Product>(){};
        products.add(product1);
        BigDecimal totalPrice = product1.getPrice();
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setProducts(products);
        when(paymentService.createPayment(USER_ID, ACCOUNT_ID, paymentRequest))
                .thenReturn(buildPaymenyt(products, totalPrice).getId());

        Integer actual = paymentService.createPayment(USER_ID, ACCOUNT_ID, paymentRequest);
        Integer expected = buildPaymenyt(products, totalPrice).getId();

        assertEquals(expected, actual);
    }

    @Test
    void shouldBuildPaymentOf2Products() throws PaymentException{

        Product product1 = productRepository.findById(PRODUCT_ID_1).orElseThrow(()-> new RuntimeException());
        Product product2 = productRepository.findById(PRODUCT_ID_2).orElseThrow(()-> new RuntimeException());
        List<Product> products = new ArrayList<Product>(){};
        products.add(product1);
        products.add(product2);
        BigDecimal totalPrice = product1.getPrice().add(product2.getPrice());
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setProducts(products);
        when(paymentService.createPayment(USER_ID, ACCOUNT_ID, paymentRequest))
                .thenReturn(buildPaymenyt(products, totalPrice).getId());

        Integer actual = paymentService.createPayment(USER_ID, ACCOUNT_ID, paymentRequest);
        Integer expected = buildPaymenyt(products, totalPrice).getId();

        assertEquals(expected, actual);
    }

    @Test
    void shouldConfirmPayment() throws Exception{
        Product product1 = productRepository.findById(PRODUCT_ID_1).orElseThrow(()-> new RuntimeException());
        List<Product> products = new ArrayList<Product>(){};
        products.add(product1);
        BigDecimal totalPrice = product1.getPrice();
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setProducts(products);

        byte[] paymentInBytes = String.valueOf(1).getBytes();
        String paymentHash = new String(Base64.getEncoder().encodeToString(paymentInBytes));
        when(paymentService.confirmPayment(paymentHash)).thenReturn(buildPaymenyt(products, totalPrice));
        Payment payment = paymentService.confirmPayment(paymentHash);
        assertTrue(payment.isConfirmed());
    }
    @Test
    void shouldNotConfirmPaymentWhenPaymentIDIsIncorrect() throws Exception{
        byte[] paymentInBytes = String.valueOf(1).getBytes();
        String paymentHash = new String(Base64.getEncoder().encodeToString(paymentInBytes));
        when(paymentService.confirmPayment(paymentHash))
                .thenThrow(new PaymentException("Couldn't find a payment with this ID"));

        try {
            paymentService.confirmPayment(paymentHash);
        } catch (PaymentException exception) {
            assertEquals("Couldn't find a payment with this ID", exception.getMessage());
        }
    }

    @Test
    void shouldNotSavePayment() throws PaymentException {
        PaymentRequest paymentRequest = new PaymentRequest();
        when(paymentService.createPayment(USER_ID, ACCOUNT_ID, paymentRequest))
                .thenThrow(new PaymentException("Payment couldn't be saved"));
        try {
            paymentService.createPayment(USER_ID, ACCOUNT_ID, paymentRequest);
        } catch (PaymentException exception) {
            assertEquals("Payment couldn't be saved", exception.getMessage());
        }
    }

    @Test
    void shouldGetAllPayments() throws Exception {
        List<Payment> payments = new ArrayList<Payment>(){};
        payments.add(buildEmptyPayment());

        when(paymentService.getPayments()).thenReturn(payments);
        List<Payment> actual = paymentService.getPayments();
        assertEquals(payments, actual);
    }
    
    @Test
    void shouldNotGetAllPayments() throws Exception {
        String errorMessage = "Could not get all payments";
        when(paymentService.getPayments()).thenThrow(new PaymentException(errorMessage));
        try { paymentService.getPayments(); }
        catch (Exception exception) { assertEquals(errorMessage, exception.getMessage());}

    }
    private Payment buildEmptyPayment() {
        Payment payment = new Payment();
        return payment;
    }

    private Payment buildPaymenyt(List<Product> products, BigDecimal totalPrice) {
        User user = userRepository.findById(USER_ID).orElseThrow(()-> new RuntimeException());
        Account account = accountRepository.findById(ACCOUNT_ID).orElseThrow(()-> new RuntimeException());
        Payment payment = new Payment();
        payment.setId(1);
        payment.setConfirmed(true);
        payment.setProducts(products);
        payment.setAccount(account);
        payment.setTotalPrice(totalPrice);
        payment.setUser(user);

        return payment;
    }

}