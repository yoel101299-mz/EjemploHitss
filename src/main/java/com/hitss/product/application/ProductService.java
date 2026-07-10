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
    public Product create(Product product) {
        product.initializeNewProduct();

        if (repositoryPort.existsBySku(product.getSku())) {
            throw new DomainException("No se puede registrar. El SKU '" + product.getSku() + "' ya existe en el sistema.");
        }

        return repositoryPort.save(product);
    }

    @Override
    public Product update(Long id, Product updateData) {
        Product existingProduct = repositoryPort.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("No se puede actualizar. Producto no encontrado con ID: " + id));

        existingProduct.updateDetails(updateData.getName(), updateData.getPrice());
        existingProduct.updateStock(updateData.getStock());

        return repositoryPort.save(existingProduct);
    }

    @Override
    public Product findById(Long id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con ID: " + id));
    }

    @Override
    public Product findBySku(String sku) {
        return repositoryPort.findBySku(sku)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con SKU: " + sku));
    }

    @Override
    public List<Product> findByName(String name) {
        return repositoryPort.findByName(name);
    }

    @Override
    public List<Product> findAll() {
        return repositoryPort.findAll();
    }

    @Override
    public void delete(Long id) {
        Product product = repositoryPort.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("No se puede eliminar. Producto no encontrado con ID: " + id));
        product.deactivate();
        repositoryPort.save(product);
    }
}