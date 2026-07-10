package com.hitss.product.ports.inbound;

import com.hitss.product.domain.model.Product;

@FunctionalInterface
public interface FindProductByIdUseCase {
    Product findById(Long id);
}