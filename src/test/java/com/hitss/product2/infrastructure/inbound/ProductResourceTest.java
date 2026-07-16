/*
package com.hitss.product.infrastructure.inbound;

import com.hitss.product.domain.model.Product;
import com.hitss.product.infrastructure.inbound.dto.ProductResponseDTO;
import com.hitss.product.infrastructure.inbound.mapper.ProductDTOMapper;
import com.hitss.product.infrastructure.inbound.mapper.ProductRequestDTO;
import com.hitss.product.ports.inbound.CreateProductUseCase;
import com.hitss.product.ports.inbound.DeleteProductUseCase;
import com.hitss.product.ports.inbound.FindAllProductsUseCase;
import com.hitss.product.ports.inbound.FindProductByIdUseCase;
import com.hitss.product.ports.inbound.FindProductBySkuUseCase;
import com.hitss.product.ports.inbound.FindProductsByNameUseCase;
import com.hitss.product.ports.inbound.UpdateProductUseCase;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductResourceTest {

    @Mock
    private CreateProductUseCase createProductUseCase;

    @Mock
    private UpdateProductUseCase updateProductUseCase;

    @Mock
    private FindProductByIdUseCase findProductByIdUseCase;

    @Mock
    private FindProductBySkuUseCase findProductBySkuUseCase;

    @Mock
    private FindProductsByNameUseCase findProductsByNameUseCase;

    @Mock
    private FindAllProductsUseCase findAllProductsUseCase;

    @Mock
    private DeleteProductUseCase deleteProductUseCase;

    @Mock
    private ProductDTOMapper dtoMapper;

    @InjectMocks
    private ProductResource resource;

    @Test
    void shouldCreateProduct() {

        ProductRequestDTO request = mock(ProductRequestDTO.class);
        Product product = mock(Product.class);
        ProductResponseDTO responseDto = mock(ProductResponseDTO.class);

        when(dtoMapper.toDomain(request)).thenReturn(product);
        when(createProductUseCase.create(product))
                .thenReturn(Uni.createFrom().item(product));
        when(dtoMapper.toResponse(product)).thenReturn(responseDto);

        try (Response response = resource.create(request).await().indefinitely()) {

            assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
            assertEquals(responseDto, response.getEntity());
        }

        verify(dtoMapper).toDomain(request);
        verify(createProductUseCase).create(product);
        verify(dtoMapper).toResponse(product);
    }

    @Test
    void shouldUpdateProduct() {

        ProductRequestDTO request = mock(ProductRequestDTO.class);
        Product product = mock(Product.class);
        ProductResponseDTO responseDto = mock(ProductResponseDTO.class);

        when(dtoMapper.toDomain(request)).thenReturn(product);
        when(updateProductUseCase.update(1L, product))
                .thenReturn(Uni.createFrom().item(product));
        when(dtoMapper.toResponse(product)).thenReturn(responseDto);

        try (Response response = resource.update(1L, request).await().indefinitely()) {

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals(responseDto, response.getEntity());
        }

        verify(dtoMapper).toDomain(request);
        verify(updateProductUseCase).update(1L, product);
        verify(dtoMapper).toResponse(product);
    }

    @Test
    void shouldGetProductById() {

        Product product = mock(Product.class);
        ProductResponseDTO responseDto = mock(ProductResponseDTO.class);

        when(findProductByIdUseCase.findById(1L))
                .thenReturn(Uni.createFrom().item(product));
        when(dtoMapper.toResponse(product)).thenReturn(responseDto);

        try (Response response = resource.getById(1L).await().indefinitely()) {

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals(responseDto, response.getEntity());
        }

        verify(findProductByIdUseCase).findById(1L);
        verify(dtoMapper).toResponse(product);
    }

    @Test
    void shouldGetProductBySku() {

        Product product = mock(Product.class);
        ProductResponseDTO responseDto = mock(ProductResponseDTO.class);

        when(findProductBySkuUseCase.findBySku("ABC123"))
                .thenReturn(Uni.createFrom().item(product));
        when(dtoMapper.toResponse(product)).thenReturn(responseDto);

        try (Response response = resource.getBySku("ABC123").await().indefinitely()) {

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals(responseDto, response.getEntity());
        }

        verify(findProductBySkuUseCase).findBySku("ABC123");
        verify(dtoMapper).toResponse(product);
    }

    @Test
    void shouldGetProductsByName() {

        List<Product> products = List.of(mock(Product.class));
        List<ProductResponseDTO> responseDtos = List.of(mock(ProductResponseDTO.class));

        when(findProductsByNameUseCase.findByName("Laptop"))
                .thenReturn(Uni.createFrom().item(products));
        when(dtoMapper.toResponseList(products)).thenReturn(responseDtos);

        try (Response response = resource.getByName("Laptop").await().indefinitely()) {

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals(responseDtos, response.getEntity());
        }

        verify(findProductsByNameUseCase).findByName("Laptop");
        verify(dtoMapper).toResponseList(products);
    }

    @Test
    void shouldGetAllProducts() {

        List<Product> products = List.of(mock(Product.class));
        List<ProductResponseDTO> responseDtos = List.of(mock(ProductResponseDTO.class));

        when(findAllProductsUseCase.findAll())
                .thenReturn(Uni.createFrom().item(products));
        when(dtoMapper.toResponseList(products)).thenReturn(responseDtos);

        try (Response response = resource.getAll().await().indefinitely()) {

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals(responseDtos, response.getEntity());
        }

        verify(findAllProductsUseCase).findAll();
        verify(dtoMapper).toResponseList(products);
    }

    @Test
    void shouldDeleteProduct() {

        when(deleteProductUseCase.delete(1L))
                .thenReturn(Uni.createFrom().voidItem());

        try (Response response = resource.delete(1L).await().indefinitely()) {

            assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        }

        verify(deleteProductUseCase).delete(1L);
    }
}*/
