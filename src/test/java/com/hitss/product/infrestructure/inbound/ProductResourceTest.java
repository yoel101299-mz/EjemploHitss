package com.hitss.product.infrestructure.inbound;

import com.hitss.product.domain.model.Product;
import com.hitss.product.ports.inbound.*;
import com.hitss.product.ports.outbound.ProductRepositoryPort;
import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.Random;
import org.hamcrest.Matchers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
public class ProductResourceTest {

    @Inject
    ProductRepositoryPort productRepository;

    private String uniqueSku;
    private Long existingProductId;

    private final Random random = new Random();

    @BeforeEach
    void setUp() {
        QuarkusTransaction.requiringNew().run(() -> {
            this.uniqueSku = getSKU();

            Product setupProduct = new Product();
            setupProduct.setSku(uniqueSku);
            setupProduct.setName("Teclado Mecánico");
            setupProduct.setPrice(BigDecimal.valueOf(150.56));
            setupProduct.setStock(3);
            setupProduct.setActive(true);

            Product saved = productRepository.save(setupProduct);
            this.existingProductId = saved.getId();
        });
    }

    @Test
    void testCreate() {
        String sku = getSKU();

        String requestBody = """
            {
                "sku": "%s",
                "name": "Ratón Gamer",
                "price": 89.90,
                "stock": 10
            }
            """.formatted(sku);

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("/api/products")
            .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("sku", is(sku))
            .body("name", is("Ratón Gamer"))
            .body("stock", is(10));
    }

    @Test
    void testUpdate() {
        String requestBody = """
            {
                "sku": "%s",
                "name": "Teclado Mecánico Modificado",
                "price": 150.56,
                "stock": 2
            }
            """.formatted(this.uniqueSku);

        given()
            .contentType(ContentType.JSON)
            .pathParam("id", existingProductId)
            .body(requestBody)
            .when()
            .put("/api/products/{id}")
            .then()
            .statusCode(200)
            .body("id", is(existingProductId.intValue()))
            .body("sku", is(this.uniqueSku))
            .body("name", is("Teclado Mecánico Modificado"))
            .body("stock", is(2));
    }

    @Test
    void testGetById() {
        given()
            .contentType(ContentType.JSON)
            .pathParam("id", existingProductId)
            .when()
            .get("/api/products/{id}")
            .then()
            .statusCode(200)
            .body("id", is(existingProductId.intValue()))
            .body("sku", is(this.uniqueSku))
            .body("name", is("Teclado Mecánico"));
    }

    @Test
    void testGetBySku() {
        given()
            .contentType(ContentType.JSON)
            .pathParam("sku", this.uniqueSku)
            .when()
            .get("/api/products/sku/{sku}")
            .then()
            .statusCode(200)
            .body("id", is(existingProductId.intValue()))
            .body("sku", is(this.uniqueSku))
            .body("name", is("Teclado Mecánico"));
    }

    @Test
    void testGetByName() {
        given()
            .contentType(ContentType.JSON)
            .queryParam("name", "Teclado")
            .when()
            .get("/api/products/search")
            .then()
            .statusCode(200)
            .body("[0].id", is(existingProductId.intValue()))
            .body("[0].sku", is(this.uniqueSku))
            .body("[0].name", is("Teclado Mecánico"));
    }

    @Test
    void testGetAll() {
        given()
            .contentType(ContentType.JSON)
            .when()
            .get("/api/products")
            .then()
            .statusCode(200)
            .body("$.size()", is(notNullValue()))
            .body("id", Matchers.hasItem(existingProductId.intValue()));
    }

    @Test
    void testDelete() {
        given()
            .contentType(ContentType.JSON)
            .pathParam("id", existingProductId)
            .when()
            .delete("/api/products/{id}")
            .then()
            .statusCode(204);
    }

    private String getSKU(){
        return "PRD-" + (1000 + random.nextInt(9000));
    }
}
