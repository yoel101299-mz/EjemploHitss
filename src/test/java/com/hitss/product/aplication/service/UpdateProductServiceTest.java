package com.hitss.product.aplication.service;

import com.hitss.product.aplication.dto.ProductDetailsDTO;
import com.hitss.product.aplication.dto.UpdateProductCommand;
import com.hitss.product.aplication.dto.mapper.ProductDetailsMapper;
import com.hitss.product.domain.exception.DomainException;
import com.hitss.product.domain.model.Money;
import com.hitss.product.domain.model.Product;
import com.hitss.product.domain.model.ProductId;
import com.hitss.product.domain.port.ProductRepository;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateProductServiceTest {

    private ProductRepository productRepository;
    private ProductDetailsMapper productDetailsMapper;

    private UpdateProductService service;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productDetailsMapper = mock(ProductDetailsMapper.class);

        service = new UpdateProductService(
                productRepository,
                productDetailsMapper
        );
    }

    @Test
    void shouldUpdateProductSuccessfully() {

        Product product = new Product(
                new ProductId("1"),
                "SKU001",
                "Laptop",
                Money.from(new BigDecimal("100")),
                10
        );

        UpdateProductCommand command = new UpdateProductCommand(
                "1",
                "Gaming Laptop",
                150,
                20
        );

        ProductDetailsDTO dto = Mockito.mock(ProductDetailsDTO.class);

        when(productRepository.findById("1"))
                .thenReturn(Uni.createFrom().item(product));

        when(productRepository.merge(any(Product.class)))
                .thenReturn(Uni.createFrom().item(product));

        when(productDetailsMapper.toDto(product))
                .thenReturn(dto);

        ProductDetailsDTO result = service
                .update(command)
                .await()
                .indefinitely();

        assertNotNull(result);
        assertSame(dto, result);

        verify(productRepository).findById("1");
        verify(productRepository).merge(product);
        verify(productDetailsMapper).toDto(product);

        assertEquals("Gaming Laptop", product.getName());
        assertEquals(20, product.getStock());
        assertEquals(Money.of(150).getAmount(), product.getPrice().getAmount());
    }

    @Test
    void shouldThrowExceptionWhenProductDoesNotExist() {

        UpdateProductCommand command = new UpdateProductCommand(
                "1",
                "Gaming Laptop",
                150,
                20
        );

        when(productRepository.findById("1"))
                .thenReturn(Uni.createFrom().nullItem());

        DomainException exception = assertThrows(
                DomainException.class,
                () -> service.update(command)
                        .await()
                        .indefinitely()
        );

        assertEquals(
                "Product not found with ID: 1",
                exception.getMessage()
        );

        verify(productRepository).findById("1");
        verify(productRepository, never()).merge(any());
        verifyNoInteractions(productDetailsMapper);
    }
}