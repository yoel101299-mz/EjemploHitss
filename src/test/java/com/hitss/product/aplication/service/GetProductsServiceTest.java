package com.hitss.product.aplication.service;

import com.hitss.product.aplication.dto.ProductDetailsDTO;
import com.hitss.product.aplication.dto.mapper.ProductDetailsMapper;
import com.hitss.product.domain.model.Product;
import com.hitss.product.domain.port.ProductRepository;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetProductsServiceTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductDetailsMapper productDetailsMapper;

    @InjectMocks
    GetProductsService getProductsService;

    @Test
    void shouldReturnAllProducts() {
        List<Product> products = List.of(
                mock(Product.class),
                mock(Product.class)
        );

        List<ProductDetailsDTO> expected = List.of(
                mock(ProductDetailsDTO.class),
                mock(ProductDetailsDTO.class)
        );

        when(productRepository.findAll())
                .thenReturn(Uni.createFrom().item(products));

        when(productDetailsMapper.toDtoList(products))
                .thenReturn(expected);

        List<ProductDetailsDTO> result =
                getProductsService.getAll().await().indefinitely();

        assertEquals(expected, result);

        verify(productRepository).findAll();
        verify(productDetailsMapper).toDtoList(products);

        verifyNoMoreInteractions(productRepository, productDetailsMapper);
    }
}