package com.payment.service;

import com.payment.entity.Account;
import com.payment.entity.Product;
import com.payment.exceptions.AccountException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductServiceTest {
    public static final int PRODUCT_ID = 1;
    private ProductService productService = mock(ProductService.class);

    @Test
    void shouldGetAccountByID() throws Exception{
        var account = buildProduct();
        when(productService.getProductById(PRODUCT_ID)).thenReturn(account);
        var actual = productService.getProductById(PRODUCT_ID);

        assertEquals(account, actual);
    }

    @Test
    void shouldThrowException() throws Exception {
        String errorMessage = "Product not found";
        when(productService.getProductById(PRODUCT_ID)).thenThrow(new AccountException(errorMessage));
        try { productService.getProductById(PRODUCT_ID); }
        catch (Exception exception) {
            assertEquals(errorMessage, exception.getMessage());
        }

    }

    private Product buildProduct() {
        var product = new Product();
        var account = new Account();
        product.setAccount(account);
        product.setId(PRODUCT_ID);
        product.setName("John Doe");
        product.setPrice(new BigDecimal(10));
        return product;
    }

}