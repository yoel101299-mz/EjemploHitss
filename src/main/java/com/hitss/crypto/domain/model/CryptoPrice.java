package com.hitss.crypto.domain.model;

public class CryptoPrice {
    private final String name;
    private final String symbol;
    private final double priceUsd;
    private final double priceMxn;

    private static final double EXCHANGE_RATE = 18.50;

    public CryptoPrice(String name, String symbol, double priceUsd) {
        this.name = name;
        this.symbol = symbol;
        this.priceUsd = priceUsd;
        this.priceMxn = priceUsd * EXCHANGE_RATE;
    }

    public String getName() { return name; }
    public String getSymbol() { return symbol; }
    public double getPriceUsd() { return priceUsd; }
    public double getPriceMxn() { return priceMxn; }
}