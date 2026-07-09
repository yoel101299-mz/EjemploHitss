package com.hitss.crypto.infrastructure.outbound.client;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;

@Path("/v1")
@RegisterRestClient(baseUri = "https://pro-api.coinmarketcap.com")
public interface CoinMarketCapClient {

    @GET
    @Path("/cryptocurrency/quotes/latest")
    Uni<CmcResponse> getLatestQuotes(
            @QueryParam("symbol") String symbol,
            @Encoded @QueryParam("convert") String currencies,
            @HeaderParam("X-CMC_PRO_API_KEY") String apiKey
    );

    record CmcResponse(Map<String, CmcData> data) {}

    record CmcData(String name, String symbol, Map<String, CmcQuote> quote) {}

    record CmcQuote(@JsonProperty("price") double price) {}
}