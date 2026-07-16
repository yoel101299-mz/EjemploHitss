package com.hitss.product.aplication.service;

import com.hitss.product.aplication.dto.ProductDetailsDTO;
import com.hitss.product.aplication.dto.UpdateProductCommand;
import com.hitss.product.aplication.dto.mapper.ProductDetailsMapper;
import com.hitss.product.aplication.port.UpdateProductUseCase;
import com.hitss.product.domain.exception.DomainException;
import com.hitss.product.domain.model.Money;
import com.hitss.product.domain.port.ProductRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UpdateProductService implements UpdateProductUseCase {

    private final ProductRepository productRepository;
    private final ProductDetailsMapper productDetailsMapper;

    public UpdateProductService(ProductRepository productRepository, ProductDetailsMapper productDetailsMapper) {
        this.productRepository = productRepository;
        this.productDetailsMapper = productDetailsMapper;
    }

    @Override
    public Uni<ProductDetailsDTO> update(UpdateProductCommand updateProductCommand) {
        return  productRepository.findById(updateProductCommand.productId())
                .onItem()
                .ifNull()
                .failWith(() -> new DomainException(
                        "Product not found with ID: " + updateProductCommand.productId()
                ))
                .flatMap(product -> {
                    product.updateProduct(
                            updateProductCommand.name(),
                            Money.of(updateProductCommand.price()),
                            updateProductCommand.stock()
                    );

                    return productRepository.merge(product);
                })
                .map(productDetailsMapper::toDto);
    }
}
