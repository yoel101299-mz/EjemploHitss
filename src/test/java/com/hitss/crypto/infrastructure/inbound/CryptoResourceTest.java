package com.hitss.crypto.infrastructure.inbound;

import com.hitss.crypto.domain.model.CryptoPrice;
import com.hitss.crypto.infrastructure.inbound.dto.CryptoResponseDTO;
import com.hitss.crypto.infrastructure.inbound.mapper.CryptoDTOMapper;
import com.hitss.crypto.ports.inbound.GetCryptoPriceUseCase;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
class CryptoResourceTest {

    @InjectMock
    GetCryptoPriceUseCase getCryptoPriceUseCase;

    @InjectMock
    CryptoDTOMapper dtoMapper;

    @Nested
    class GetPriceTests {

        @Test
        void shouldReturn200WhenSymbolExists() {
            String symbol = "BTC";
            CryptoPrice mockDomainPrice = new CryptoPrice("Bitcoin", "BTC", 60000.00);

            CryptoResponseDTO mockDto = new CryptoResponseDTO();

            when(getCryptoPriceUseCase.execute(anyString()))
                    .thenReturn(Uni.createFrom().item(mockDomainPrice));

            when(dtoMapper.toResponse(any(CryptoPrice.class)))
                    .thenReturn(mockDto);

            given()
                    .queryParam("symbol", symbol)
                    .when()
                    .get("/crypto/price")
                    .then()
                    .statusCode(200)
                    .contentType("application/json");

            verify(getCryptoPriceUseCase, times(1)).execute(anyString());
        }

        @Test
        void shouldReturn404WhenUseCaseFails() {
            String symbol = "INVALID";
            String errorMessage = "The symbol cannot be empty or was not found";

            when(getCryptoPriceUseCase.execute(symbol))
                    .thenReturn(Uni.createFrom().failure(new RuntimeException(errorMessage)));

            given()
                    .queryParam("symbol", symbol)
                    .when()
                    .get("/crypto/price")
                    .then()
                    .statusCode(404)
                    .contentType("application/json")
                    .body("message", equalTo(errorMessage));

            verify(getCryptoPriceUseCase, times(1)).execute(symbol);
            verifyNoInteractions(dtoMapper);
        }
    }
}