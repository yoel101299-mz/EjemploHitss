package com.hitss.crypto.infrastructure.config;

import com.hitss.crypto.application.CryptoService;
import com.hitss.crypto.ports.inbound.GetCryptoPriceUseCase;
import com.hitss.crypto.ports.outbound.CryptoSearchPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;

@Dependent
public class CryptoBeanConfiguration {

    @Produces
    @ApplicationScoped
    public GetCryptoPriceUseCase getCryptoPriceUseCase(CryptoSearchPort searchPort) {
        return new CryptoService(searchPort);
    }
}