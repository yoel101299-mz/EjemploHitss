package com.hitss.product.aplication.dto;

public record CreateProductCommand(
        String sku,
        String name,
        double price,
        Integer stock
) {
}
