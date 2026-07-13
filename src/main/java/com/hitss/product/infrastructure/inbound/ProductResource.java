package com.hitss.product.infrastructure.inbound;

import com.hitss.product.domain.model.Product;
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
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
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
    private final FindProductByIdUseCase findProductByIdUseCase;
    private final FindProductBySkuUseCase findProductBySkuUseCase;
    private final FindProductsByNameUseCase findProductsByNameUseCase;
    private final FindAllProductsUseCase findAllProductsUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final ProductDTOMapper dtoMapper;

    @POST
    public Uni<Response> create(ProductRequestDTO request) {
        Product domain = dtoMapper.toDomain(request);
        return createProductUseCase.create(domain)
            .map(created -> Response.status(Response.Status.CREATED)
                .entity(dtoMapper.toResponse(created))
                .build());
    }

    @PUT
    @Path("/{id}")
    public Uni<Response> update(@PathParam("id") Long id, ProductRequestDTO request) {
        Product domain = dtoMapper.toDomain(request);
        return updateProductUseCase.update(id, domain)
            .map(updated -> Response.ok(dtoMapper.toResponse(updated)).build());
    }

    @GET
    @Path("/{id}")
    public Uni<Response> getById(@PathParam("id") Long id) {
        return findProductByIdUseCase.findById(id)
            .map(product -> Response.ok(dtoMapper.toResponse(product)).build());
    }

    @GET
    @Path("/sku/{sku}")
    public Uni<Response> getBySku(@PathParam("sku") String sku) {
        return findProductBySkuUseCase.findBySku(sku)
            .map(product -> Response.ok(dtoMapper.toResponse(product)).build());
    }

    @GET
    @Path("/search")
    public Uni<Response> getByName(@QueryParam("name") String name) {
        return findProductsByNameUseCase.findByName(name)
            .map(products -> Response.ok(dtoMapper.toResponseList(products)).build());
    }

    @GET
    public Uni<Response> getAll() {
        return findAllProductsUseCase.findAll()
            .map(products -> Response.ok(dtoMapper.toResponseList(products)).build());
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> delete(@PathParam("id") Long id) {
        return deleteProductUseCase.delete(id)
            .replaceWith(() -> Response.noContent().build());
    }
}