package com.hitss.product.domain.model;

import com.hitss.product.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void shouldCreateProductSuccessfully() {
        ProductId id = new ProductId("1");
        Money price = new Money(new BigDecimal("100.00"));

        Product product = new Product(
                id,
                "SKU001",
                "Laptop",
                price,
                10
        );

        assertEquals(id, product.getId());
        assertEquals("SKU001", product.getSku());
        assertEquals("Laptop", product.getName());
        assertEquals(price, product.getPrice());
        assertEquals(10, product.getStock());
        assertTrue(product.isActive());
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        Product product = new Product(
                new ProductId("1"),
                "SKU001",
                "Laptop",
                new Money(new BigDecimal("100.00")),
                10
        );

        Money newPrice = new Money(new BigDecimal("150.00"));

        product.updateProduct(
                "Gaming Laptop",
                newPrice,
                20
        );

        assertEquals("Gaming Laptop", product.getName());
        assertEquals(newPrice, product.getPrice());
        assertEquals(20, product.getStock());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingInactiveProduct() {
        Product product = new Product(
                new ProductId("1"),
                "SKU001",
                "Laptop",
                new Money(new BigDecimal("100.00")),
                10
        );

        product.deactivate();

        DomainException exception = assertThrows(
                DomainException.class,
                () -> product.updateProduct(
                        "Gaming Laptop",
                        new Money(new BigDecimal("150.00")),
                        20
                )
        );

        assertEquals("Cannot update inactive product", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        Product product = new Product(
                new ProductId("1"),
                "SKU001",
                "Laptop",
                new Money(new BigDecimal("100.00")),
                10
        );

        DomainException exception = assertThrows(
                DomainException.class,
                () -> product.updateProduct(
                        "",
                        new Money(new BigDecimal("150.00")),
                        20
                )
        );

        assertEquals("Product name cannot be empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        Product product = new Product(
                new ProductId("1"),
                "SKU001",
                "Laptop",
                new Money(new BigDecimal("100.00")),
                10
        );

        DomainException exception = assertThrows(
                DomainException.class,
                () -> product.updateProduct(
                        null,
                        new Money(new BigDecimal("150.00")),
                        20
                )
        );

        assertEquals("Product name cannot be empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenStockIsNegative() {
        Product product = new Product(
                new ProductId("1"),
                "SKU001",
                "Laptop",
                new Money(new BigDecimal("100.00")),
                10
        );

        DomainException exception = assertThrows(
                DomainException.class,
                () -> product.updateProduct(
                        "Gaming Laptop",
                        new Money(new BigDecimal("150.00")),
                        -1
                )
        );

        assertEquals("Stock cannot be negative", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenStockIsNull() {
        Product product = new Product(
                new ProductId("1"),
                "SKU001",
                "Laptop",
                new Money(new BigDecimal("100.00")),
                10
        );

        DomainException exception = assertThrows(
                DomainException.class,
                () -> product.updateProduct(
                        "Gaming Laptop",
                        new Money(new BigDecimal("150.00")),
                        null
                )
        );

        assertEquals("Stock cannot be negative", exception.getMessage());
    }

    @Test
    void shouldDeactivateProduct() {
        Product product = new Product(
                new ProductId("1"),
                "SKU001",
                "Laptop",
                new Money(new BigDecimal("100.00")),
                10
        );

        product.deactivate();

        assertFalse(product.isActive());
    }
}