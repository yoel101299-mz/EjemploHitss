package com.hitss.product.infrastructure.inbound;

import com.hitss.product.domain.model.Product;
import com.hitss.product.infrastructure.inbound.mapper.ProductDTOMapper;
import com.hitss.product.infrastructure.inbound.mapper.ProductRequestDTO;
import com.hitss.product.ports.inbound.ProductUseCase;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    public final ProductUseCase productUseCase;
    public final ProductDTOMapper dtoMapper;

    public ProductResource(ProductUseCase productUseCase, ProductDTOMapper dtoMapper) {
        this.productUseCase = productUseCase;
        this.dtoMapper = dtoMapper;
    }

    @POST
    @Transactional
    public Response create(ProductRequestDTO request) {
        Product domain = dtoMapper.toDomain(request);
        Product created = productUseCase.createProduct(domain);
        return Response.status(Response.Status.CREATED).entity(dtoMapper.toResponse(created)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, ProductRequestDTO request) {
        Product domain = dtoMapper.toDomain(request);
        Product updated = productUseCase.updateProduct(id, domain);
        return Response.ok(dtoMapper.toResponse(updated)).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Product product = productUseCase.findById(id);
        return Response.ok(dtoMapper.toResponse(product)).build();
    }

    @GET
    @Path("/sku/{sku}")
    public Response getBySku(@PathParam("sku") String sku) {
        Product product = productUseCase.findBySku(sku);
        return Response.ok(dtoMapper.toResponse(product)).build();
    }

    @GET
    @Path("/search")
    public Response getByName(@QueryParam("name") String name) {
        List<Product> products = productUseCase.findByName(name);
        return Response.ok(dtoMapper.toResponseList(products)).build();
    }

    @GET
    public Response getAll() {
        List<Product> products = productUseCase.findAll();
        return Response.ok(dtoMapper.toResponseList(products)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        productUseCase.deleteProduct(id);
        return Response.noContent().build();
    }
}