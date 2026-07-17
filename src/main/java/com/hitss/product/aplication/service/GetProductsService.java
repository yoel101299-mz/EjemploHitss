package com.hitss.product.aplication.service;

import com.hitss.product.aplication.dto.ProductDetailsDTO;
import com.hitss.product.aplication.dto.mapper.ProductDetailsMapper;
import com.hitss.product.aplication.port.GetProductsUseCase;
import com.hitss.product.domain.port.ProductRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class GetProductsService implements GetProductsUseCase {

    private final ProductRepository productRepository;
    private final ProductDetailsMapper productDetailsMapper;

    @Inject
    public GetProductsService(ProductRepository productRepository, ProductDetailsMapper productDetailsMapper) {
        this.productRepository = productRepository;
        this.productDetailsMapper = productDetailsMapper;
    }

    @Override
    public Uni<List<ProductDetailsDTO>> getAll() {
        return productRepository.findAll().map(productDetailsMapper::toDtoList);
    }
}
