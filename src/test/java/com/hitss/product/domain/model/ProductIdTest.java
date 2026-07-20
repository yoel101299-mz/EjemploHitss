package com.hitss.product.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductIdTest {

    @Test
    void shouldCreateProductIdFromValue() {
        ProductId productId = ProductId.of("123");

        assertEquals("123", productId.value());
    }

    @Test
    void shouldGenerateNewProductId() {
        ProductId productId = ProductId.newId();

        assertNotNull(productId);
        assertNotNull(productId.value());
        assertFalse(productId.value().isBlank());
    }
}