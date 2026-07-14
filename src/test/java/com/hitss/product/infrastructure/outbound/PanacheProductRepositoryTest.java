package com.hitss.product.infrastructure.outbound;

import com.hitss.product.domain.model.Product;
import com.hitss.product.infrastructure.outbound.mapper.ProductEntityMapper;
import io.quarkus.test.hibernate.reactive.panache.TransactionalUniAsserter;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.RunOnVertxContext;
import io.quarkus.test.vertx.UniAsserter;
import io.vertx.junit5.VertxTestContext;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class PanacheProductRepositoryTest {

    @Inject
    PanacheProductRepository repository;

    private Product createProduct() {
        int random = (int) (Math.random() * 9000) + 1000;
        return Product.builder()
            .sku("PRO-" + random)
            .name("Producto Test")
            .price(new BigDecimal("100.00"))
            .stock(10)
            .active(true)
            .build();
    }

    @Test
    @RunOnVertxContext
    void shouldSaveNewProduct(TransactionalUniAsserter asserter) {
        Product product = createProduct();

        asserter.assertThat(
            () -> repository.save(product),
            saved -> {

                assertNotNull(saved);
                assertNotNull(saved.getId());

                assertEquals(
                    product.getSku(),
                    saved.getSku()
                );

                assertEquals(
                    product.getName(),
                    saved.getName()
                );

                assertTrue(saved.isActive());
            }
        );
    }

    @Test
    @RunOnVertxContext
    void shouldFindProductBySku(TransactionalUniAsserter  asserter) {
        asserter.execute(() ->
            repository.save(createProduct())
        );

        asserter.assertThat(
            () -> repository.findBySku("PRO-1234"),
            product -> {
                assertNotNull(product);
                assertEquals("PRO-1234", product.getSku());
            }
        );
    }

    @Test
    @RunOnVertxContext
    void shouldFindAllActiveProducts(TransactionalUniAsserter  asserter) {
        asserter.execute(() ->
            repository.save(createProduct())
        );

        asserter.assertThat(
            () -> repository.findAll(),
            products -> {
                assertNotNull(products);
                assertFalse(products.isEmpty());
            }
        );
    }

    @Test
    @RunOnVertxContext
    void shouldFindProductsByName(TransactionalUniAsserter asserter) {
        asserter.execute(() ->
            repository.save(createProduct())
        );

        asserter.assertThat(
            () -> repository.findByName("Producto"),
            products -> {
                assertFalse(products.isEmpty());
                assertEquals(
                    "Producto Test",
                    products.getFirst().getName()
                );
            }
        );
    }

    @Test
    @RunOnVertxContext
    void shouldReturnTrueWhenSkuExists(TransactionalUniAsserter asserter) {
        asserter.execute(() ->
            repository.save(createProduct())
        );

        asserter.assertThat(
            () -> repository.existsBySku("PRO-1234"),
            exists -> assertTrue(exists)
        );
    }

    @Test
    @RunOnVertxContext
    void shouldReturnFalseWhenSkuDoesNotExist(TransactionalUniAsserter  asserter) {
        asserter.assertThat(
            () -> repository.existsBySku("XXX-9999"),
            exists -> assertFalse(exists)
        );
    }

    @Test
    @RunOnVertxContext
    void shouldUpdateExistingProduct(TransactionalUniAsserter asserter) {
        asserter.assertThat(
            () -> repository.save(createProduct()),
            saved -> {

                saved.setName("Producto Actualizado");

                asserter.assertThat(
                    () -> repository.save(saved),
                    updated -> {
                        assertEquals(
                            "Producto Actualizado",
                            updated.getName()
                        );

                    }
                );
            }
        );
    }

}