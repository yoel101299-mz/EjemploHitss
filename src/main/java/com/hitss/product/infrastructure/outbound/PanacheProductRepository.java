package com.hitss.product.infrastructure.outbound;

import com.hitss.product.domain.model.Product;
import com.hitss.product.infrastructure.outbound.entity.ProductEntity;
import com.hitss.product.infrastructure.outbound.mapper.ProductEntityMapper;
import com.hitss.product.ports.outbound.ProductRepositoryPort;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;

import java.util.List;

@ApplicationScoped
public class PanacheProductRepository implements ProductRepositoryPort {

    private static final String FIND_BY_SKU_QUERY = "sku = ?1 and active = true";
    private static final String FIND_BY_NAME_QUERY = "lower(name) like lower(?1) and active = true";
    private static final String FIND_ALL_ACTIVE_QUERY = "active = true";
    private static final String COUNT_BY_SKU_QUERY = "sku = ?1";
    private static final long ZERO = 0L;

    @ApplicationScoped
    public static class InternalPanacheRepository implements PanacheRepository<ProductEntity> { }

    private final ProductEntityMapper mapper;
    private final InternalPanacheRepository internalRepository;

    public PanacheProductRepository(ProductEntityMapper mapper, InternalPanacheRepository internalRepository) {
        this.mapper = mapper;
        this.internalRepository = internalRepository;
    }

    @Override
    public Uni<Product> findById(Long id) {
        return Panache.withSession(() ->
            internalRepository.findById(id).map(mapper::toDomain)
        );
    }

    @Override
    public Uni<Product> save(Product product) {
        ProductEntity entity = mapper.toEntity(product);

        if (entity.getId() == null) {
            return internalRepository.persist(entity)
                    .map(v -> mapper.toDomain(entity));
        }

        return internalRepository.getSession()
                .flatMap(session -> session.merge(entity))
                .map(mapper::toDomain);
    }

    @Override
    public Uni<Product> findBySku(String sku) {
        return Panache.withSession(() ->
            internalRepository.find(FIND_BY_SKU_QUERY, sku)
                .firstResult()
                .map(mapper::toDomain)
        );
    }

    @Override
    public Uni<List<Product>> findByName(String name) {
        return Panache.withSession(() ->
            internalRepository.list(FIND_BY_NAME_QUERY, "%" + name + "%")
                .map(mapper::toDomainList)
        );
    }

    @Override
    public Uni<List<Product>> findAll() {
        return Panache.withSession(() ->
            internalRepository.list(FIND_ALL_ACTIVE_QUERY)
                .map(mapper::toDomainList)
        );
    }

    @Override
    public Uni<Boolean> existsBySku(String sku) {
        return Panache.withSession(() ->
            internalRepository.count(COUNT_BY_SKU_QUERY, sku)
                .map(count -> count > ZERO)
        );
    }
}