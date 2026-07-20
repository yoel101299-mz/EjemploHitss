package com.hitss.crypto.infrastructure.inbound;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusIntegrationTest
@QuarkusTestResource(CoinMarketCapWireMock.class)
class CryptoResourceIT {

    @Test
    void shouldReturnCryptoPrice() {

        given()
                .contentType(ContentType.JSON)
                .queryParam("symbol", "BTC")
                .when()
                .get("/crypto/price")
                .then()
                .statusCode(200)
                .body("name", equalTo("Bitcoin"))
                .body("symbol", equalTo("BTC"))
                .body("priceUsd", equalTo(123456.78F));
    }

    @Test
    void shouldReturn404WhenSymbolDoesNotExist() {

        given()
                .contentType(ContentType.JSON)
                .queryParam("symbol", "ETH")
                .when()
                .get("/crypto/price")
                .then()
                .statusCode(404)
                .body("message", equalTo("Symbol not found: ETH"));
    }

    @Test
    void shouldReturn404WhenSymbolIsBlank() {

        given()
                .contentType(ContentType.JSON)
                .queryParam("symbol", "")
                .when()
                .get("/crypto/price")
                .then()
                .statusCode(404)
                .body("message", equalTo("The symbol cannot be empty"));
    }
}