package com.hitss.crypto.infrastructure.outbound.client;

import com.hitss.crypto.domain.model.CryptoPrice;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CoinMarketCapAdapterTest {

    private CoinMarketCapClient cmcClient;
    private CoinMarketCapAdapter adapter;

    @BeforeEach
    void setUp() {
        cmcClient = Mockito.mock(CoinMarketCapClient.class);
        adapter = new CoinMarketCapAdapter(cmcClient, "fake-api-key");
    }

    @Test
    void shouldReturnCryptoPrice() {
        CoinMarketCapClient.CmcQuote quote =
                new CoinMarketCapClient.CmcQuote(105000.50);

        CoinMarketCapClient.CmcData data =
                new CoinMarketCapClient.CmcData(
                        "Bitcoin",
                        "BTC",
                        Map.of("USD", quote)
                );

        CoinMarketCapClient.CmcResponse response =
                new CoinMarketCapClient.CmcResponse(
                        Map.of("BTC", data)
                );

        when(cmcClient.getLatestQuotes("BTC", "USD", "fake-api-key"))
                .thenReturn(Uni.createFrom().item(response));

        CryptoPrice result = adapter.fetchPrice("BTC")
                .await().indefinitely();

        assertNotNull(result);
        assertEquals("Bitcoin", result.getName());
        assertEquals("BTC", result.getSymbol());
        assertEquals(105000.50, result.getPriceUsd());
    }

    @Test
    void shouldThrowExceptionWhenSymbolDoesNotExist() {

        CoinMarketCapClient.CmcResponse response =
                new CoinMarketCapClient.CmcResponse(Map.of());

        when(cmcClient.getLatestQuotes("BTC", "USD", "fake-api-key"))
                .thenReturn(Uni.createFrom().item(response));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> adapter.fetchPrice("BTC").await().indefinitely()
        );

        assertEquals("Symbol not found: BTC", exception.getMessage());
    }
}