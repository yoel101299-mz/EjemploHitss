package com.hitss.crypto.infrastructure.inbound;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class CoinMarketCapWireMock implements QuarkusTestResourceLifecycleManager {

    private WireMockServer server;

    @Override
    public Map<String, String> start() {

        server = new WireMockServer(8089);
        server.start();

        configureFor("localhost", 8089);

        server.stubFor(get(urlPathEqualTo("/v1/cryptocurrency/quotes/latest"))
                .withQueryParam("symbol", equalTo("ETH"))
                .willReturn(okJson("""
            {
              "data": {}
            }
            """)));

        server.stubFor(get(urlPathEqualTo("/v1/cryptocurrency/quotes/latest"))
                .withQueryParam("symbol", equalTo("BTC"))
                .withQueryParam("convert", equalTo("USD"))
                .willReturn(okJson("""
            {
              "data": {
                "BTC": {
                  "name": "Bitcoin",
                  "symbol": "BTC",
                  "quote": {
                    "USD": {
                      "price": 123456.78
                    }
                  }
                }
              }
            }
            """)));

        return Map.of(
                "quarkus.rest-client.coinmarketcap.url", "http://localhost:8089",
                "CMC_API_KEY", "test-api-key"
        );
    }

    @Override
    public void stop() {
        if (server != null) {
            server.stop();
        }
    }
}