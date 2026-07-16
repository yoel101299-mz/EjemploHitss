package com.hitss.product.infrastructure.adapter;

import com.hitss.product.domain.model.Product;
import com.hitss.product.domain.port.ProductRepository;
import com.hitss.product.infrastructure.mapper.ProductEntityMapper;
import com.hitss.product.infrastructure.persistence.ProductEntity;
import com.hitss.product.infrastructure.repository.InternalPanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.UUID;

@ApplicationScoped
@AllArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {
    private final ProductEntityMapper mapper;
    private final InternalPanacheRepository internalRepository;

    @Override
    public Uni<Product> save(Product product) {
        ProductEntity productEntity = mapper.toEntity(product);

        return internalRepository.persist(productEntity)
                .map(mapper::toDomain);
    }

    @Override
    public Uni<Product> findById(String id) {
        return internalRepository.findById(UUID.fromString(id)).map(mapper::toDomain);
    }

    @Override
    public Uni<Product> merge(Product product) {
        ProductEntity entity = mapper.toEntity(product);

        return internalRepository.getSession()
                .flatMap(session -> session.merge(entity))
                .replaceWith(product);
    }
}
