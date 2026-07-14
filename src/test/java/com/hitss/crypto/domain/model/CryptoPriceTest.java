package com.hitss.crypto.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CryptoPriceTest {

    @Test
    void shouldCalculateMxnPriceCorrectly() {
        String name = "Bitcoin";
        String symbol = "BTC";
        double priceUsd = 60000.00;

        double expectedMxn = priceUsd * 18.50;

        CryptoPrice cryptoPrice = new CryptoPrice(name, symbol, priceUsd);

        assertNotNull(cryptoPrice);
        assertEquals("Bitcoin", cryptoPrice.getName());
        assertEquals("BTC", cryptoPrice.getSymbol());
        assertEquals(60000.00, cryptoPrice.getPriceUsd());

        assertEquals(expectedMxn, cryptoPrice.getPriceMxn(), 0.001);
    }

    @Test
    void shouldHandleZeroPrice() {
        CryptoPrice cryptoPrice = new CryptoPrice("Ethereum", "ETH", 0.0);

        assertEquals(0.0, cryptoPrice.getPriceUsd());
        assertEquals(0.0, cryptoPrice.getPriceMxn(), 0.001);
    }
}