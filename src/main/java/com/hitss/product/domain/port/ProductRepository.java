package com.hitss.product.domain.port;

import com.hitss.product.domain.model.Product;
import io.smallrye.mutiny.Uni;

public interface ProductRepository {
    Uni<Product> save(Product product);
    Uni<Product> findById(String id);
    Uni<Product> merge(Product product);
}
