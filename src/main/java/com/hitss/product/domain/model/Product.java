package com.hitss.product.domain.model;

import com.hitss.shared.domain.exception.DomainException;

import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
@Builder
public class Product {
    private Long id;
    private String sku;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private boolean active;

    public void initializeNewProduct() {
        validateBusinessRules(this.sku, this.name, this.price, this.stock);
        this.active = true;
    }

    public void updateDetails(String name, BigDecimal price) {
        validateBusinessRules(this.sku, name, price, this.stock);
        this.name = name;
        this.price = price;
    }

    public void updateStock(Integer newStock) {
        if (newStock == null || newStock < 0) {
            throw new DomainException("El stock no puede ser negativo ni nulo.");
        }
        this.stock = newStock;
    }

    public void deactivate() {
        this.active = false;
    }

    private void validateBusinessRules(String sku, String name, BigDecimal price, Integer stock) {
        if (sku == null || !sku.matches("^[A-Z]{3}-\\d{4}$")) {
            throw new DomainException("El SKU es inválido. Debe cumplir el formato estándar (Ej: PRO-1234).");
        }
        if (name == null || name.trim().length() < 3) {
            throw new DomainException("El nombre del producto debe tener al menos 3 caracteres.");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException("El precio del producto debe ser estrictamente mayor a cero.");
        }
        if (stock == null || stock < 0) {
            throw new DomainException("El stock inicial no puede ser negativo.");
        }
    }
}