package com.hitss.product.infrastructure.repository;

import com.hitss.product.infrastructure.persistence.ProductEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class InternalPanacheRepository implements PanacheRepositoryBase<ProductEntity, UUID> { }