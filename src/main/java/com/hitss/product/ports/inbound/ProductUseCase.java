package com.hitss.product.ports.inbound;

import com.hitss.product.domain.model.Product;

import java.util.List;

public interface ProductUseCase {
    Product createProduct(Product product);
    Product updateProduct(Long id, Product product);
    Product findById(Long id);
    Product findBySku(String sku);
    List<Product> findByName(String name);
    List<Product> findAll();
    void deleteProduct(Long id);
}