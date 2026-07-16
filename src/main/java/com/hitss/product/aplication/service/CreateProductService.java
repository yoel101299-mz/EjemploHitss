package com.hitss.product.aplication.service;

import com.hitss.product.aplication.dto.CreateProductCommand;
import com.hitss.product.aplication.dto.ProductDetailsDTO;
import com.hitss.product.aplication.dto.mapper.ProductDetailsMapper;
import com.hitss.product.aplication.port.CreateProductUseCase;
import com.hitss.product.domain.model.Money;
import com.hitss.product.domain.model.Product;
import com.hitss.product.domain.model.ProductId;
import com.hitss.product.domain.port.ProductRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CreateProductService implements CreateProductUseCase {

    private final ProductRepository productRepository;
    private final ProductDetailsMapper productDetailsMapper;

    @Inject
    public CreateProductService(ProductRepository productRepository, ProductDetailsMapper productDetailsMapper) {
        this.productRepository = productRepository;
        this.productDetailsMapper = productDetailsMapper;
    }

    @Override
    public Uni<ProductDetailsDTO> create(CreateProductCommand createProductCommand) {
        Product product = new Product(
                ProductId.newId(),
                createProductCommand.sku(),
                createProductCommand.name(),
                Money.of((createProductCommand.price())),
                createProductCommand.stock()
        );
        return productRepository.save(product)
                .map(productDetailsMapper::toDto);
    }
}
