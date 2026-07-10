package com.hitss.product.ports.inbound;

@FunctionalInterface
public interface DeleteProductUseCase {
    void delete(Long id);
}