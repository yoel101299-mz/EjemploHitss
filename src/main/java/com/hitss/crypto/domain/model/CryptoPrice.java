package com.hitss.crypto.domain.model;

import lombok.Getter;

@Getter
public class CryptoPrice {
    String name;
    String symbol;
    double priceUsd;
    double priceMxn;

    private static final double EXCHANGE_RATE = 18.50;

    public CryptoPrice(String name, String symbol, double priceUsd) {
        this.name = name;
        this.symbol = symbol;
        this.priceUsd = priceUsd;
        this.priceMxn = priceUsd * EXCHANGE_RATE;
    }
}