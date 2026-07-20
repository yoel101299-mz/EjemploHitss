package com.hitss.crypto.infrastructure.config;

import com.hitss.crypto.ports.inbound.GetCryptoPriceUseCase;
import com.hitss.crypto.ports.outbound.CryptoSearchPort;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class CryptoBeanConfigurationTest {

    @Inject
    GetCryptoPriceUseCase getCryptoPriceUseCase;

    @InjectMock
    CryptoSearchPort cryptoSearchPort;

    @Test
    void shouldInitializeConfigAndProduceUseCaseDirectly() {
        CryptoBeanConfiguration config = new CryptoBeanConfiguration();
        assertNotNull(config);

        GetCryptoPriceUseCase producedUseCase = config.getCryptoPriceUseCase(cryptoSearchPort);

        assertNotNull(producedUseCase, "El método getCryptoPriceUseCase debería retornar una instancia válida");
    }

    @Test
    void shouldVerifyCdiInjection() {
        assertNotNull(getCryptoPriceUseCase, "El contenedor de Quarkus debería inyectar el caso de uso correctamente");
    }
}