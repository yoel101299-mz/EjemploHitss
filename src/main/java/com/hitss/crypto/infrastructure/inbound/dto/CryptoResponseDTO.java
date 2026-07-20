package com.hitss.crypto.infrastructure.inbound.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class CryptoResponseDTO {
    private String name;
    private String symbol;
    private double priceUsd;
    private double priceMxn;
}