package com.hitss.product.domain.model;

import java.util.UUID;

public record ProductId(String value) {
    public static ProductId newId() {
        return new ProductId(UUID.randomUUID().toString());
    }
    public static ProductId of(String value) {
        return new ProductId(value);
    }
}
