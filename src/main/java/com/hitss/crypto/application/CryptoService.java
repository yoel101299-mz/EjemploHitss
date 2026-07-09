package com.hitss.crypto.application;

import com.hitss.crypto.domain.model.CryptoPrice;
import com.hitss.crypto.ports.inbound.GetCryptoPriceUseCase;
import com.hitss.crypto.ports.outbound.CryptoSearchPort;
import io.smallrye.mutiny.Uni;

public class CryptoService implements GetCryptoPriceUseCase {

    private final CryptoSearchPort cryptoSearchPort;

    public CryptoService(CryptoSearchPort cryptoSearchPort) {
        this.cryptoSearchPort = cryptoSearchPort;
    }

    @Override
    public Uni<CryptoPrice> execute(String symbol) {
        if (symbol == null || symbol.isBlank()) {
            return Uni.createFrom().failure(new IllegalArgumentException("The symbol cannot be empty"));
        }
        return cryptoSearchPort.fetchPrice(symbol.toUpperCase());
    }
}