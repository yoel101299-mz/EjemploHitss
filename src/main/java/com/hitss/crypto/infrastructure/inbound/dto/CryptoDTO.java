package com.hitss.crypto.infrastructure.inbound.dto;

public record CryptoDTO(
        String name,
        String symbol,
        double priceUsd,
        double priceMxn
) {}