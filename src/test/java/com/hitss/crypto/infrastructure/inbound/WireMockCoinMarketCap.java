package com.hitss.crypto.infrastructure.inbound;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.Map;

public class WireMockCoinMarketCap implements QuarkusTestResourceLifecycleManager {

    private WireMockServer wireMockServer;

    @Override
    public Map<String, String> start() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());
        wireMockServer.start();

        System.out.println("WireMock port = " + wireMockServer.port());

        WireMock.configureFor("localhost", wireMockServer.port());

        return Map.of(
                "quarkus.rest-client.\"com.hitss.crypto.infrastructure.outbound.client.CoinMarketCapClient\".url",
                wireMockServer.baseUrl(),
                "CMC_API_KEY", "wiremock-test-api-key"
        );
    }

    @Override
    public void stop() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }
}