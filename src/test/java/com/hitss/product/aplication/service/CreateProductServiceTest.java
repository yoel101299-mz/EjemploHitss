package com.hitss.product.aplication.service;

import com.hitss.product.aplication.dto.CreateProductCommand;
import com.hitss.product.aplication.dto.ProductDetailsDTO;
import com.hitss.product.aplication.dto.mapper.ProductDetailsMapper;
import com.hitss.product.domain.model.Product;
import com.hitss.product.domain.port.ProductRepository;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductDetailsMapper productDetailsMapper;

    @InjectMocks
    private CreateProductService service;

    @Test
    void shouldCreateProduct() {
        CreateProductCommand command = new CreateProductCommand(
                "SKU-001",
                "Laptop",
                1500.0,
                10
        );

        Product savedProduct = mock(Product.class);
        ProductDetailsDTO expected = mock(ProductDetailsDTO.class);

        when(productRepository.save(any(Product.class)))
                .thenReturn(Uni.createFrom().item(savedProduct));

        when(productDetailsMapper.toDto(savedProduct))
                .thenReturn(expected);

        ProductDetailsDTO result = service.create(command)
                .await()
                .indefinitely();

        assertSame(expected, result);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(captor.capture());

        Product productSent = captor.getValue();

        assertNotNull(productSent.getId());
        assertEquals("SKU-001", productSent.getSku());
        assertEquals("Laptop", productSent.getName());
        assertEquals(
                BigDecimal.valueOf(1500.0),
                productSent.getPrice().getAmount()
        );
        assertEquals(10, productSent.getStock());

        verify(productDetailsMapper).toDto(savedProduct);
        verifyNoMoreInteractions(productRepository, productDetailsMapper);
    }
}