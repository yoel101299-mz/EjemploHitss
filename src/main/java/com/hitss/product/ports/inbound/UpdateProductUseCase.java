package com.hitss.product.ports.inbound;

import com.hitss.product.domain.model.Product;

@FunctionalInterface
public interface UpdateProductUseCase {
    Product update(Long id, Product product);
}