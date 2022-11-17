package com.payment.dao;

import com.payment.entity.Product;
import com.payment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductDAO {

    @Autowired
    private ProductRepository productRepository;

    public Product getProduct(Product p) {
        return productRepository.findById(p.getId()).orElseThrow(() -> new RuntimeException());
    }
}
