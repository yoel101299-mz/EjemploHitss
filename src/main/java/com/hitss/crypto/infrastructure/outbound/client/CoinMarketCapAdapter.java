package com.hitss.crypto.infrastructure.outbound.client;

import com.hitss.crypto.domain.model.CryptoPrice;
import com.hitss.crypto.ports.outbound.CryptoSearchPort;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class CoinMarketCapAdapter implements CryptoSearchPort {

    private final String USD = "USD";

    private final CoinMarketCapClient cmcClient;
    private final String apiKey;

    public CoinMarketCapAdapter(
            @RestClient CoinMarketCapClient cmcClient,
            @ConfigProperty(name = "CMC_API_KEY") String apiKey
    ) {
        this.cmcClient = cmcClient;
        this.apiKey = apiKey;
    }

    @Override
    public Uni<CryptoPrice> fetchPrice(String symbol) {
        return cmcClient.getLatestQuotes(symbol, USD, apiKey)
                .onItem().transform(cmcResponse -> {
                    CoinMarketCapClient.CmcData data = cmcResponse.data().get(symbol);
                    if (data == null) {
                        throw new RuntimeException("Symbol not found: " + symbol);
                    }
                    double priceUsd = data.quote().get(USD).price();
                    return new CryptoPrice(data.name(), data.symbol(), priceUsd);
                });
    }
}