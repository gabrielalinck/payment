package com.payment.dao;

import com.payment.entity.Product;
import com.payment.exceptions.ProductException;
import com.payment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ProductDAO {

    @Autowired
    private ProductRepository productRepository;

    public Product getProduct(Integer productID) throws ProductException {
        return productRepository.findById(productID).orElseThrow(() -> new ProductException("Product not found"));
    }
}
