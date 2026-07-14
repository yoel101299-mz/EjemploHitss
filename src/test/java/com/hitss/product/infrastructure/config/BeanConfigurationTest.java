package com.hitss.product.infrastructure.config;

import com.hitss.product.application.ProductService;
import com.hitss.product.domain.exception.ProductNotFoundException;
import com.hitss.product.ports.outbound.ProductRepositoryPort;
import com.hitss.shared.domain.exception.DomainException;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class BeanConfigurationTest {

    @Inject
    ProductService productService;


    @Test
    void shouldInjectProductService() {
        assertNotNull(productService);
    }

    @Test
    void shouldMapProductNotFoundExceptionTo404() {

        BeanConfiguration.DomainExceptionMapper mapper =
                new BeanConfiguration.DomainExceptionMapper();

        ProductNotFoundException exception =
                new ProductNotFoundException("Producto no encontrado");


        try (Response response = mapper.toResponse(exception)) {

            assertEquals(404, response.getStatus());

            Map<String,String> entity =
                    (Map<String,String>) response.getEntity();

            assertEquals(
                    "Producto no encontrado",
                    entity.get("error")
            );
        }
    }

    @Test
    void shouldMapGenericDomainExceptionTo400() {

        BeanConfiguration.DomainExceptionMapper mapper =
                new BeanConfiguration.DomainExceptionMapper();


        DomainException exception =
                new DomainException("Error de negocio general") {};


        try (Response response = mapper.toResponse(exception)) {

            assertEquals(400, response.getStatus());

            Map<String,String> entity =
                    (Map<String,String>) response.getEntity();

            assertEquals(
                    "Error de negocio general",
                    entity.get("error")
            );
        }
    }
}