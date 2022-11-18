package com.payment.service;

import com.payment.dao.AccountDAO;
import com.payment.dao.ProductDAO;
import com.payment.entity.Account;
import com.payment.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductDAO dao;

    public ProductService(ProductDAO dao) {this.dao = dao;}

    public Product getProductById(Integer product) throws Exception {
        return dao.getProduct(product);
    }
}
