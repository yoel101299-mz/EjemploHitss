package com.hitss.product.infrastructure.inbound.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductRequestDTO {
    private String sku;
    private String name;
    private BigDecimal price;
    private Integer stock;
}