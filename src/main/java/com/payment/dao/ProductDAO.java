package com.payment.dao;

import com.payment.entity.Product;
import com.payment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ProductDAO {

    @Autowired
    private ProductRepository productRepository;

    public Product getProduct(Product p) {
        return productRepository.findById(p.getId()).orElseThrow(() -> new RuntimeException());
    }
}
