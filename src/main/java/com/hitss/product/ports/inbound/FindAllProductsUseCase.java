package com.hitss.product.ports.inbound;

import com.hitss.product.domain.model.Product;

import java.util.List;

@FunctionalInterface
public interface FindAllProductsUseCase {
    List<Product> findAll();
}