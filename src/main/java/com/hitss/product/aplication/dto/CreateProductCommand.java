package com.hitss.product.aplication.dto;

import java.math.BigDecimal;

public record CreateProductCommand(
        String sku,
        String name,
        double price,
        Integer stock
) {
}
