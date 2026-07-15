package com.hitss.product.infrastructure.inbound.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import java.math.BigDecimal;

@Data
@RegisterForReflection
public class ProductResponseDTO {
    private Long id;
    private String sku;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private boolean active;
}