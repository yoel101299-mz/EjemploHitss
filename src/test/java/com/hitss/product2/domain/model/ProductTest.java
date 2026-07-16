package com.hitss.product2.domain.model;

import com.hitss.shared.domain.exception.DomainException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Nested
    class InitializationTests {

        @Test
        void shouldInitializeNewProductSuccessfully() {
            Product product = Product.builder()
                    .sku("PRO-1234")
                    .name("Teclado Mecánico")
                    .price(new BigDecimal("150.50"))
                    .stock(5)
                    .build();

            product.initializeNewProduct();

            assertTrue(product.isActive(), "El producto debería estar activo al inicializarse");
        }

        @ParameterizedTest
        @NullSource
        @ValueSource(strings = {"invalid-sku", "PR-123", "PRO1234", "pro-1234", "ABCD-1234"})
        void shouldThrowExceptionWhenSkuIsInvalid(String invalidSku) {
            Product product = Product.builder()
                    .sku(invalidSku)
                    .name("Teclado Mecánico")
                    .price(new BigDecimal("150.50"))
                    .stock(5)
                    .build();

            DomainException exception = assertThrows(DomainException.class, product::initializeNewProduct);
            assertTrue(exception.getMessage().contains("SKU es inválido"));
        }

        @ParameterizedTest
        @NullSource
        @ValueSource(strings = {"", "  ", "ab"})
        void shouldThrowExceptionWhenNameIsInvalid(String invalidName) {
            Product product = Product.builder()
                    .sku("PRO-1234")
                    .name(invalidName)
                    .price(new BigDecimal("150.50"))
                    .stock(5)
                    .build();

            DomainException exception = assertThrows(DomainException.class, product::initializeNewProduct);
            assertTrue(exception.getMessage().contains("nombre del producto debe tener al menos 3 caracteres"));
        }

        @Test
        void shouldThrowExceptionWhenPriceIsInvalid() {
            Product productZeroPrice = Product.builder().sku("PRO-1234").name("Teclado").price(BigDecimal.ZERO).stock(5).build();
            assertThrows(DomainException.class, productZeroPrice::initializeNewProduct);

            Product productNegativePrice = Product.builder().sku("PRO-1234").name("Teclado").price(new BigDecimal("-1.00")).stock(5).build();
            assertThrows(DomainException.class, productNegativePrice::initializeNewProduct);

            Product productNullPrice = Product.builder().sku("PRO-1234").name("Teclado").price(null).stock(5).build();
            assertThrows(DomainException.class, productNullPrice::initializeNewProduct);
        }

        @Test
        void shouldThrowExceptionWhenInitialStockIsInvalid() {
            Product productNegativeStock = Product.builder().sku("PRO-1234").name("Teclado").price(BigDecimal.TEN).stock(-1).build();
            assertThrows(DomainException.class, productNegativeStock::initializeNewProduct);

            Product productNullStock = Product.builder().sku("PRO-1234").name("Teclado").price(BigDecimal.TEN).stock(null).build();
            assertThrows(DomainException.class, productNullStock::initializeNewProduct);
        }
    }

    @Nested
    class UpdateTests {

        @Test
        void shouldUpdateDetailsSuccessfully() {
            Product product = Product.builder().sku("PRO-1234").name("Teclado").price(BigDecimal.TEN).stock(5).build();

            product.updateDetails("Nuevo Ratón Gamer", new BigDecimal("89.90"));

            assertEquals("Nuevo Ratón Gamer", product.getName());
            assertEquals(new BigDecimal("89.90"), product.getPrice());
        }

        @Test
        void shouldUpdateStockSuccessfully() {
            Product product = Product.builder().stock(5).build();

            product.updateStock(20);

            assertEquals(20, product.getStock());
        }

        @Test
        void shouldThrowExceptionWhenNewStockIsInvalid() {
            Product product = Product.builder().stock(5).build();
            assertThrows(DomainException.class, () -> product.updateStock(null));
            assertThrows(DomainException.class, () -> product.updateStock(-5));
        }

        @Test
        void shouldDeactivateProductSuccessfully() {
            Product product = Product.builder().active(true).build();
            product.deactivate();
            assertFalse(product.isActive());
        }
    }
}