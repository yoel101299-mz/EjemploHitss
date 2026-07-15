package com.hitss.product.infrastructure.outbound;

import com.hitss.product.domain.model.Product;
import io.quarkus.test.hibernate.reactive.panache.TransactionalUniAsserter;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.RunOnVertxContext;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class PanacheProductRepositoryTest {

    @Inject
    PanacheProductRepository repository;
    private static final AtomicInteger skuCounter = new AtomicInteger(
            ThreadLocalRandom.current().nextInt(1000, 9000)
    );

    private Product createProduct() {

        int number = skuCounter.incrementAndGet();

        if (number > 9999) {
            skuCounter.set(1000);
            number = skuCounter.incrementAndGet();
        }

        String validSku = String.format("PRD-%04d", number);

        System.out.println(">>>>>> " + validSku);

        return Product.builder()
                .sku(validSku)
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
                    assertEquals(product.getSku(), saved.getSku());
                    assertEquals(product.getName(), saved.getName());
                    assertTrue(saved.isActive());
                }
        );
    }

    @Test
    @RunOnVertxContext
    void shouldFindProductBySku(TransactionalUniAsserter asserter) {
        Product product = createProduct();
        String targetSku = product.getSku();

        asserter.execute(() -> repository.save(product));

        asserter.assertThat(
                () -> repository.findBySku(targetSku),
                found -> {
                    assertNotNull(found);
                    assertEquals(targetSku, found.getSku());
                }
        );
    }

    @Test
    @RunOnVertxContext
    void shouldFindAllActiveProducts(TransactionalUniAsserter asserter) {
        asserter.execute(() -> repository.save(createProduct()));

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
        asserter.execute(() -> repository.save(createProduct()));

        asserter.assertThat(
                () -> repository.findByName("Producto"),
                products -> {
                    assertFalse(products.isEmpty());
                    assertEquals("Producto Test", products.getFirst().getName());
                }
        );
    }

    @Test
    @RunOnVertxContext
    void shouldReturnTrueWhenSkuExists(TransactionalUniAsserter asserter) {
        Product product = createProduct();
        String targetSku = product.getSku();

        asserter.execute(() -> repository.save(product));

        asserter.assertThat(
                () -> repository.existsBySku(targetSku),
                exists -> assertTrue(exists)
        );
    }

    @Test
    @RunOnVertxContext
    void shouldReturnFalseWhenSkuDoesNotExist(TransactionalUniAsserter asserter) {
        String fakeSku = "XXX-" + UUID.randomUUID().toString().substring(0, 8);

        asserter.assertThat(
                () -> repository.existsBySku(fakeSku),
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
                            updated -> assertEquals("Producto Actualizado", updated.getName())
                    );
                }
        );
    }
}