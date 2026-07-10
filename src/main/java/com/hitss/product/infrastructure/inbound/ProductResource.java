package com.hitss.product.infrastructure.inbound;

import com.hitss.product.domain.model.Product;
import com.hitss.product.infrastructure.inbound.mapper.ProductDTOMapper;
import com.hitss.product.infrastructure.inbound.mapper.ProductRequestDTO;
import com.hitss.product.ports.inbound.*;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;

import java.util.List;

@Path("/api/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class ProductResource {

    public final CreateProductUseCase createProductUseCase;
    public final UpdateProductUseCase updateProductUseCase;
    public final FindProductByIdUseCase findProductByIdUseCase;
    public final FindProductBySkuUseCase findProductBySkuUseCase;
    public final FindProductsByNameUseCase findProductsByNameUseCase;
    public final FindAllProductsUseCase findAllProductsUseCase;
    public final DeleteProductUseCase deleteProductUseCase;
    public final ProductDTOMapper dtoMapper;

    @POST
    @Transactional
    public Response create(ProductRequestDTO request) {
        Product domain = dtoMapper.toDomain(request);
        Product created = createProductUseCase.create(domain);
        return Response.status(Response.Status.CREATED).entity(dtoMapper.toResponse(created)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, ProductRequestDTO request) {
        Product domain = dtoMapper.toDomain(request);
        Product updated = updateProductUseCase.update(id, domain);
        return Response.ok(dtoMapper.toResponse(updated)).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Product product = findProductByIdUseCase.findById(id);
        return Response.ok(dtoMapper.toResponse(product)).build();
    }

    @GET
    @Path("/sku/{sku}")
    public Response getBySku(@PathParam("sku") String sku) {
        Product product = findProductBySkuUseCase.findBySku(sku);
        return Response.ok(dtoMapper.toResponse(product)).build();
    }

    @GET
    @Path("/search")
    public Response getByName(@QueryParam("name") String name) {
        List<Product> products = findProductsByNameUseCase.findByName(name);
        return Response.ok(dtoMapper.toResponseList(products)).build();
    }

    @GET
    public Response getAll() {
        List<Product> products = findAllProductsUseCase.findAll();
        return Response.ok(dtoMapper.toResponseList(products)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        deleteProductUseCase.delete(id);
        return Response.noContent().build();
    }
}