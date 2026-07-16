package com.hitss.product.aplication.dto;

public record ProductDetailsDTO(
        String id,
        String sku,
        String name,
        double price,
        Integer stock,
        boolean active
) {
}
