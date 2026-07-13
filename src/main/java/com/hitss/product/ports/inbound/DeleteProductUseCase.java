package com.hitss.product.ports.inbound;

import io.smallrye.mutiny.Uni;

@FunctionalInterface
public interface DeleteProductUseCase {
    Uni<Void> delete(Long id);
}