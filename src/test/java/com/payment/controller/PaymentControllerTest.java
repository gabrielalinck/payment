package com.payment.controller;

import com.payment.controller.reques.PaymentRequest;
import com.payment.entity.Payment;
import com.payment.entity.Product;
import com.payment.exceptions.PaymentException;
import com.payment.repository.AccountRepository;
import com.payment.repository.PaymentRepository;
import com.payment.repository.ProductRepository;
import com.payment.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class PaymentControllerTest {
    public static final int PRODUCT1_ID = 1;
    public static final int PRODUCT2_ID = 2;
    public static final int PAYMENT_ID = 1;
    public static final int WRONG_PRODUCT_ID = 1000000000;
    public static final String PRODUCT_NOT_FOUND = "Product not found";

    public static final int USER_ID = 1;
    public static final int ACCOUNT_ID = 1;
    public static final String COULDNT_FIND_A_PAYMENT_WITH_THIS_ID = "Couldn't find a payment with this ID";
    public static final int WRONG_PAYMENT_ID = 1000000000;
    public static final String PAYMENT_COULDNT_BE_SAVED = "Payment couldn't be saved";
    @Autowired
    private PaymentController controller;

    @Resource
    private PaymentRepository paymentRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private AccountRepository accountRepository;

    @Resource
    private ProductRepository productRepository;

    @Test
    void shouldCreatePaymentWith1Product() throws Exception{
        Integer userId = 1;
        Integer accountId = 1;
        PaymentRequest paymentRequest = getPaymentRequest(PRODUCT1_ID);
        String actual = controller.createPayment(userId, accountId, paymentRequest);
        String expected = getPaymentString();

        assertEquals(expected, actual);
    }

    @Test
    void shouldCreatePaymentWith2Products() throws Exception{
        Integer userId = 1;
        Integer accountId = 1;
        PaymentRequest paymentRequest = getPaymentRequest(PRODUCT1_ID);
        List<Product> products = paymentRequest.getProducts();
        Product product2 = new Product();
        product2.setId(PRODUCT2_ID);
        products.add(product2);
        String actual = controller.createPayment(userId, accountId, paymentRequest);
        String expected = getPaymentString();

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowExceptionWhenProductIdIsWrong() throws Exception{
        Integer userId = 1;
        Integer accountId = 1;
        PaymentRequest paymentRequest = getPaymentRequest(WRONG_PRODUCT_ID);

        try { controller.createPayment(userId, accountId, paymentRequest); }
        catch (PaymentException exception) { assertEquals(PRODUCT_NOT_FOUND, exception.getMessage()); }
    }

    @Test
    void shouldThrowExceptionWhenUserIsWrong() throws Exception{
        Integer userId = 1000000000;
        Integer accountId = 1;
        PaymentRequest paymentRequest = getPaymentRequest(PRODUCT1_ID);

        try { controller.createPayment(userId, accountId, paymentRequest); }
        catch (PaymentException exception) { assertEquals(PAYMENT_COULDNT_BE_SAVED, exception.getMessage()); }
    }

    @Test
    void shouldThrowExceptionWhenAccountIsWrong() throws Exception{
        Integer userId = 1;
        Integer accountId = 1000000000;
        PaymentRequest paymentRequest = getPaymentRequest(PRODUCT1_ID);

        try { controller.createPayment(userId, accountId, paymentRequest); }
        catch (PaymentException exception) { assertEquals(PAYMENT_COULDNT_BE_SAVED, exception.getMessage()); }
    }

    @Test
    void shouldThrowExceptionWhenPaymentRequestIsEmpty() throws PaymentException {
        Integer userId = 1;
        Integer accountId = 1;
        PaymentRequest paymentRequest = new PaymentRequest();

        try { controller.createPayment(userId, accountId, paymentRequest); }
        catch (PaymentException exception) { assertEquals(PRODUCT_NOT_FOUND, exception.getMessage()); }
    }

    @Test
    void shouldgetAllPaymentsWithNoPayments() throws Exception{
        List<Payment> all = controller.getAll();
        assert all.isEmpty();
    }

    @Test
    void shouldGetAllPaymentsWhenPaymentIsCreated() throws Exception {
        PaymentRequest paymentRequest = getPaymentRequest(PRODUCT1_ID);
        controller.createPayment(USER_ID, ACCOUNT_ID, paymentRequest);
        List<Payment> actual = controller.getAll();
        assertEquals(actual.size(), 1);
    }

    @Test
    void shouldConfirmPayment() throws Exception{
        PaymentRequest paymentRequest = getPaymentRequest(USER_ID);
        controller.createPayment(USER_ID, ACCOUNT_ID, paymentRequest);
        Payment actual = controller.confirmPayment(getPaymentHash(PAYMENT_ID));
        assertTrue(actual.isConfirmed());
    }

    @Test
    void shouldNotConfirmPaymentIfPaymentIsNotDone() throws Exception {
        try { controller.confirmPayment(getPaymentHash(PAYMENT_ID)); }
        catch (PaymentException exception) {
            assertEquals(COULDNT_FIND_A_PAYMENT_WITH_THIS_ID, exception.getMessage());
        }

    }

    @Test
    void shouldNotConfirmPaymentIfPaymentIsWithWrongID() throws Exception {
        PaymentRequest paymentRequest = getPaymentRequest(USER_ID);
        controller.createPayment(USER_ID, ACCOUNT_ID, paymentRequest);
        try { controller.confirmPayment(getPaymentHash(WRONG_PAYMENT_ID)); }
        catch (PaymentException exception) {
            assertEquals(COULDNT_FIND_A_PAYMENT_WITH_THIS_ID, exception.getMessage());
        }

    }

    private String getPaymentString() {
        String paymentString = new StringBuilder("http://localhost:8080/")
                .append("api/payments/")
                .append(getPaymentHash(PAYMENT_ID))
                .toString();

        return paymentString;
    }

    private static String getPaymentHash(Integer paymentID) {
        byte[] paymentInBytes = String.valueOf(paymentID).getBytes();
        String paymentHash = Base64.getEncoder().encodeToString(paymentInBytes);
        return paymentHash;
    }

    private static PaymentRequest getPaymentRequest(Integer productId) {
        PaymentRequest paymentRequest = new PaymentRequest();
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(productId);
        products.add(product1);
        paymentRequest.setProducts(products);
        return paymentRequest;
    }
}