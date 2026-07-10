package com.hitss.product.infrastructure.config;

import com.hitss.product.application.ProductService;
import com.hitss.product.domain.exception.ProductNotFoundException;
import com.hitss.product.ports.outbound.ProductRepositoryPort;
import com.hitss.shared.domain.exception.DomainException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;

@Dependent
public class BeanConfiguration {

    @Produces
    @ApplicationScoped
    public ProductService productUseCase(ProductRepositoryPort repositoryPort) {
        return new ProductService(repositoryPort);
    }

    @Provider
    public static class DomainExceptionMapper implements ExceptionMapper<DomainException> {
        @Override
        public Response toResponse(DomainException exception) {
            int status = (exception instanceof ProductNotFoundException) ? 404 : 400;
            return Response.status(status)
                    .entity(Map.of("error", exception.getMessage()))
                    .build();
        }
    }
}