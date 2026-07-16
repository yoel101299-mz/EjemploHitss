package com.hitss.product.infrastructure.web.dto;

import com.hitss.product.aplication.dto.ProductDetailsDTO;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import java.math.BigDecimal;

public record ProductResponse(
        String id,
        String sku,
        String name,
        double price,
        Integer stock,
        boolean active
) {
    public static ProductResponse fromDTO(ProductDetailsDTO productDetailsDTO) {
        return new ProductResponse(
                productDetailsDTO.id(),
                productDetailsDTO.sku(),
                productDetailsDTO.name(),
                productDetailsDTO.price(),
                productDetailsDTO.stock(),
                productDetailsDTO.active()
        );
    }
}