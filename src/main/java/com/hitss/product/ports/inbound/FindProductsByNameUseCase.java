package com.hitss.product.ports.inbound;

import com.hitss.product.domain.model.Product;
import io.smallrye.mutiny.Uni;

import java.util.List;

@FunctionalInterface
public interface FindProductsByNameUseCase {
    Uni<List<Product>> findByName(String name);
}