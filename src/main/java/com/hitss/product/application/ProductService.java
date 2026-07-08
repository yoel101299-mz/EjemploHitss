package com.hitss.product.application;

import com.hitss.product.domain.exception.ProductNotFoundException;
import com.hitss.product.domain.model.Product;
import com.hitss.product.ports.inbound.ProductUseCase;
import com.hitss.product.ports.outbound.ProductRepositoryPort;
import com.hitss.shared.domain.exception.DomainException;

import java.util.List;

public class ProductService implements ProductUseCase {

    private final ProductRepositoryPort repositoryPort;

    public ProductService(ProductRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public Product createProduct(Product product) {
        product.initializeNewProduct();

        if (repositoryPort.existsBySku(product.getSku())) {
            throw new DomainException("No se puede registrar. El SKU '" + product.getSku() + "' ya existe en el sistema.");
        }

        return repositoryPort.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product updateData) {
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
    public void deleteProduct(Long id) {
        Product product = repositoryPort.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("No se puede eliminar. Producto no encontrado con ID: " + id));
        product.deactivate();
        repositoryPort.save(product);
    }
}