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
        return internalRepository.find("sku = ?1 and active = true", sku).firstResultOptional().map(mapper::toDomain);
    }

    @Override
    public List<Product> findByName(String name) {
        List<ProductEntity> entities = internalRepository.list("lower(name) like lower(?1) and active = true", "%" + name + "%");
        return mapper.toDomainList(entities);
    }

    @Override
    public List<Product> findAll() {
        return mapper.toDomainList(internalRepository.list("active = true"));
    }

    @Override
    public boolean existsBySku(String sku) {
        return internalRepository.count("sku = ?1", sku) > 0;
    }

    @Override
    public void deleteById(Long id) {

    }
}