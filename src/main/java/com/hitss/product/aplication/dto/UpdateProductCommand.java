package com.hitss.product.aplication.dto;

public record UpdateProductCommand(
        String productId,
        String name,
        double price,
        Integer stock
) {
}
