package com.hitss.product.ports.inbound;

import com.hitss.product.domain.model.Product;
import io.smallrye.mutiny.Uni;

@FunctionalInterface
public interface FindProductByIdUseCase {
    Uni<Product> findById(Long id);
}