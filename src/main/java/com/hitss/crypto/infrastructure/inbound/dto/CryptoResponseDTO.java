package com.hitss.crypto.infrastructure.inbound.dto;

import lombok.Data;

@Data
public class CryptoResponseDTO {
    private String name;
    private String symbol;
    private double priceUsd;
    private double priceMxn;
}