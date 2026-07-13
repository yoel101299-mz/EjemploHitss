package com.hitss.product.ports.outbound;

import com.hitss.product.domain.model.Product;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface ProductRepositoryPort {
    Uni<Product> findById(Long id);
    Uni<Product> save(Product product);
    Uni<Product> findBySku(String sku);
    Uni<List<Product>> findByName(String name);
    Uni<List<Product>> findAll();
    Uni<Boolean> existsBySku(String sku);
}