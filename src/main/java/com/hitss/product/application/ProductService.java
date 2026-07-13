package com.hitss.product.application;

import com.hitss.product.domain.exception.ProductNotFoundException;
import com.hitss.product.domain.model.Product;
import com.hitss.product.ports.inbound.CreateProductUseCase;
import com.hitss.product.ports.inbound.DeleteProductUseCase;
import com.hitss.product.ports.inbound.FindAllProductsUseCase;
import com.hitss.product.ports.inbound.FindProductByIdUseCase;
import com.hitss.product.ports.inbound.FindProductBySkuUseCase;
import com.hitss.product.ports.inbound.FindProductsByNameUseCase;
import com.hitss.product.ports.inbound.UpdateProductUseCase;
import com.hitss.product.ports.outbound.ProductRepositoryPort;
import com.hitss.shared.domain.exception.DomainException;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;

import java.util.List;

public class ProductService implements CreateProductUseCase,
        UpdateProductUseCase,
        FindProductByIdUseCase,
        FindProductBySkuUseCase,
        FindProductsByNameUseCase,
        FindAllProductsUseCase,
        DeleteProductUseCase {

    private final ProductRepositoryPort repositoryPort;

    public ProductService(ProductRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public Uni<Product> create(Product product) {
        return Panache.withTransaction(() ->
            repositoryPort.existsBySku(product.getSku())
                .flatMap(exists -> {
                    if (exists) {
                        return Uni.createFrom().failure(new DomainException(
                                "No se puede registrar. El SKU '" + product.getSku() + "' ya existe en el sistema."));
                    }

                    product.initializeNewProduct();
                    return repositoryPort.save(product);
                })
        );
    }

    @Override
    public Uni<Product> update(Long id, Product updateData) {
        return Panache.withTransaction(() ->
            repositoryPort.findById(id)
                .onItem().ifNull().failWith(() ->
                        new ProductNotFoundException("No se puede actualizar. Producto no encontrado con ID: " + id))
                .flatMap(existingProduct -> {
                    existingProduct.updateDetails(updateData.getName(), updateData.getPrice());
                    existingProduct.updateStock(updateData.getStock());
                    return repositoryPort.save(existingProduct);
                })
        );
    }

    @Override
    @WithSession
    public Uni<Product> findById(Long id) {
        return repositoryPort.findById(id)
            .onItem().ifNull().failWith(() -> new ProductNotFoundException("Producto no encontrado con ID: " + id));
    }

    @Override
    @WithSession
    public Uni<Product> findBySku(String sku) {
        return repositoryPort.findBySku(sku)
            .onItem().ifNull().failWith(() -> new ProductNotFoundException("Producto no encontrado con SKU: " + sku));
    }

    @Override
    @WithSession
    public Uni<List<Product>> findByName(String name) {
        return repositoryPort.findByName(name);
    }

    @Override
    @WithSession
    public Uni<List<Product>> findAll() {
        return repositoryPort.findAll();
    }

    @Override
    public Uni<Void> delete(Long id) {
        return Panache.withTransaction(() ->
            repositoryPort.findById(id)
                .onItem().ifNull().failWith(() ->
                        new ProductNotFoundException("No se puede eliminar. Producto no encontrado con ID: " + id))
                .flatMap(product -> {
                    product.deactivate();
                    return repositoryPort.save(product);
                })
                .replaceWithVoid()
        );
    }
}