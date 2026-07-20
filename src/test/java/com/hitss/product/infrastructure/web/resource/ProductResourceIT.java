package com.hitss.product.infrastructure.web.resource;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusIntegrationTest
class ProductResourceIT {

    @Test
    void shouldCreateProduct() {

        String id =
                given()
                        .contentType(ContentType.JSON)
                        .body("""
                                {
                                  "sku":"SKU-001",
                                  "name":"Laptop",
                                  "price":1500,
                                  "stock":10
                                }
                                """)
                        .when()
                        .post("/api/products")
                        .then()
                        .statusCode(200)
                        .body("id", notNullValue())
                        .body("sku", equalTo("SKU-001"))
                        .body("name", equalTo("Laptop"))
                        .body("price", equalTo(1500.0F))
                        .body("stock", equalTo(10))
                        .body("active", equalTo(true))
                        .extract()
                        .path("id");

        given()
                .when()
                .get("/api/products")
                .then()
                .statusCode(200)
                .body("id", hasItem(id));
    }

    @Test
    void shouldUpdateProduct() {

        String id =
                given()
                        .contentType(ContentType.JSON)
                        .body("""
                                {
                                  "sku":"SKU-002",
                                  "name":"Mouse",
                                  "price":500,
                                  "stock":5
                                }
                                """)
                        .when()
                        .post("/api/products")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("id");

        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "name":"Mouse Gamer",
                          "price":650,
                          "stock":20
                        }
                        """)
                .when()
                .put("/api/products/" + id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id))
                .body("name", equalTo("Mouse Gamer"))
                .body("price", equalTo(650.0F))
                .body("stock", equalTo(20))
                .body("active", equalTo(true));
    }

    @Test
    void shouldGetAllProducts() {

        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "sku":"SKU-003",
                          "name":"Keyboard",
                          "price":700,
                          "stock":15
                        }
                        """)
                .when()
                .post("/api/products")
                .then()
                .statusCode(200);

        given()
                .when()
                .get("/api/products")
                .then()
                .statusCode(200)
                .body("$", not(empty()))
                .body("sku", hasItem("SKU-003"))
                .body("name", hasItem("Keyboard"));
    }

}