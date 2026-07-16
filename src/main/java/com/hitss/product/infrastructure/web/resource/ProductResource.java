package com.hitss.product.infrastructure.web.resource;

import com.hitss.product.aplication.dto.CreateProductCommand;
import com.hitss.product.aplication.dto.UpdateProductCommand;
import com.hitss.product.aplication.port.CreateProductUseCase;
import com.hitss.product.aplication.port.UpdateProductUseCase;
import com.hitss.product.infrastructure.web.dto.CreateProductRequest;
import com.hitss.product.infrastructure.web.dto.ProductResponse;
import com.hitss.product.infrastructure.web.dto.UpdateProductRequest;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;

@Path("/api/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class ProductResource {

    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;

    @POST
    @WithTransaction
    public Uni<Response> create(CreateProductRequest request) {
        var command = new CreateProductCommand(
                request.sku(),
                request.name(),
                request.price(),
                request.stock()
        );
        return createProductUseCase.create(command)
                .map(created -> Response.status(Response.Status.CREATED)
                        .entity(ProductResponse.fromDTO(created))
                        .build());
    }

    @PUT
    @Path("/{id}")
    @WithTransaction
    public Uni<Response> update(@PathParam("id") String id, UpdateProductRequest request) {
        var command = new UpdateProductCommand(
            id,
            request.name(),
            request.price(),
            request.stock()
        );

        return updateProductUseCase.update(command)
                .map(created -> Response.status(Response.Status.CREATED)
                        .entity(ProductResponse.fromDTO(created))
                        .build());
    }
}
