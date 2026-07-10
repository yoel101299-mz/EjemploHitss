package com.hitss.product.infrastructure.outbound;

import com.hitss.product.domain.model.Product;
import com.hitss.product.infrastructure.outbound.entity.ProductEntity;
import com.hitss.product.infrastructure.outbound.mapper.ProductEntityMapper;
import com.hitss.product.ports.outbound.ProductRepositoryPort;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PanacheProductRepository implements ProductRepositoryPort {

    private static final String FIND_BY_SKU_QUERY = "sku = ?1 and active = true";
    private static final String FIND_BY_NAME_QUERY = "lower(name) like lower(?1) and active = true";
    private static final String FIND_ALL_ACTIVE_QUERY = "active = true";
    private static final String COUNT_BY_SKU_QUERY = "sku = ?1";
    private static final int ZERO = 0;

    @ApplicationScoped
    public static class InternalPanacheRepository implements PanacheRepository<ProductEntity> { }

    private final ProductEntityMapper mapper;
    private final InternalPanacheRepository internalRepository;

    public PanacheProductRepository(ProductEntityMapper mapper, InternalPanacheRepository internalRepository) {
        this.mapper = mapper;
        this.internalRepository = internalRepository;
    }

    @Override
    public Optional<Product> findById(Long id) {
        // Al no heredar directamente en la clase principal, ya no hay choque de métodos
        return internalRepository.findByIdOptional(id).map(mapper::toDomain);
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = mapper.toEntity(product);
        if (entity.getId() == null) {
            internalRepository.persist(entity);
        } else {
            internalRepository.getEntityManager().merge(entity);
        }
        return mapper.toDomain(entity);
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        return internalRepository.find(FIND_BY_SKU_QUERY, sku).firstResultOptional().map(mapper::toDomain);
    }

    @Override
    public List<Product> findByName(String name) {
        List<ProductEntity> entities = internalRepository.list(FIND_BY_NAME_QUERY, "%" + name + "%");
        return mapper.toDomainList(entities);
    }

    @Override
    public List<Product> findAll() {
        return mapper.toDomainList(internalRepository.list(FIND_ALL_ACTIVE_QUERY));
    }

    @Override
    public boolean existsBySku(String sku) {
        return internalRepository.count(COUNT_BY_SKU_QUERY, sku) > ZERO;
    }
}