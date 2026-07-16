package com.hitss.product.domain.model;

import com.hitss.product.domain.exception.DomainException;

public class Product {
    private ProductId id;
    private String sku;
    private String name;
    private Money price;
    private Integer stock;
    private boolean active;

    public Product(ProductId id, String sku, String name, Money price, Integer stock) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.active = true;
    }

    public void updateProduct(
            String name,
            Money price,
            Integer stock
    ) {
        if (!active) {
            throw new DomainException("Cannot update inactive product");
        }

        updateDetails(name, price);
        updateStock(stock);
    }


    private void updateDetails(String name, Money price) {
        if (name == null || name.isBlank()) {
            throw new DomainException("Product name cannot be empty");
        }

        this.name = name;
        this.price = price;
    }


    private void updateStock(Integer stock) {
        if (stock == null || stock < 0) {
            throw new DomainException("Stock cannot be negative");
        }

        this.stock = stock;
    }

    public void deactivate() {
        this.active = false;
    }

    public ProductId getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public boolean isActive() {
        return active;
    }
}
