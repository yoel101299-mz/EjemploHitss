package com.hitss.product.ports.outbound;

import com.hitss.product.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryPort {
    Product save(Product product);
    Optional<Product> findById(Long id);
    Optional<Product> findBySku(String sku);
    List<Product> findByName(String name);
    List<Product> findAll();
    boolean existsBySku(String sku);
}