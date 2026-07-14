package com.hitss.crypto.application;

import com.hitss.crypto.domain.model.CryptoPrice;
import com.hitss.crypto.ports.inbound.GetCryptoPriceUseCase;
import com.hitss.crypto.ports.outbound.CryptoSearchPort;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

@QuarkusTest
class CryptoServiceTest {

    @InjectMock
    CryptoSearchPort cryptoSearchPort;

    @Inject
    GetCryptoPriceUseCase cryptoService;

    @Nested
    class HappyPathTests {

        @Test
        void shouldReturnCryptoPriceAndConvertToUpper() {
            String inputSymbol = "btc";
            String expectedUpperSymbol = "BTC";
            CryptoPrice mockPrice = new CryptoPrice("Bitcoin", expectedUpperSymbol, 60000.00);

            when(cryptoSearchPort.fetchPrice(expectedUpperSymbol))
                    .thenReturn(Uni.createFrom().item(mockPrice));

            cryptoService.execute(inputSymbol)
                    .subscribe().withSubscriber(UniAssertSubscriber.create())
                    .awaitItem()
                    .assertItem(mockPrice)
                    .assertCompleted();

            verify(cryptoSearchPort, times(1)).fetchPrice(expectedUpperSymbol);
        }
    }

    @Nested
    class ValidationTests {

        @Test
        void shouldFailWhenSymbolIsNull() {
            cryptoService.execute(null)
                    .subscribe().withSubscriber(UniAssertSubscriber.create())
                    .awaitFailure()
                    .assertFailedWith(IllegalArgumentException.class, "The symbol cannot be empty");

            verifyNoInteractions(cryptoSearchPort);
        }

        @Test
        void shouldFailWhenSymbolIsBlank() {
            cryptoService.execute("   ")
                    .subscribe().withSubscriber(UniAssertSubscriber.create())
                    .awaitFailure()
                    .assertFailedWith(IllegalArgumentException.class, "The symbol cannot be empty");

            verifyNoInteractions(cryptoSearchPort);
        }
    }
}