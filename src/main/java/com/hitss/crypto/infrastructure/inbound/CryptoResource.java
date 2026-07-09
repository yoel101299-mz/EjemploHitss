package com.hitss.crypto.infrastructure.inbound;

import com.hitss.crypto.infrastructure.inbound.dto.CryptoDTO;
import com.hitss.crypto.ports.inbound.GetCryptoPriceUseCase;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/crypto")
@Produces(MediaType.APPLICATION_JSON)
public class CryptoResource {

    private final GetCryptoPriceUseCase getCryptoPriceUseCase;

    public CryptoResource(GetCryptoPriceUseCase getCryptoPriceUseCase) {
        this.getCryptoPriceUseCase = getCryptoPriceUseCase;
    }

    @GET
    @Path("/price")
        public Uni<Response> getPrice(@QueryParam("symbol") String symbol) {
        return getCryptoPriceUseCase.execute(symbol)
                .onItem().transform(domainPrice -> new CryptoDTO(
                        domainPrice.getName(),
                        domainPrice.getSymbol(),
                        domainPrice.getPriceUsd(),
                        domainPrice.getPriceMxn()
                ))
                .onItem().transform(dto -> Response.ok(dto).build())
                .onFailure().recoverWithItem(err -> Response.status(Response.Status.NOT_FOUND)
                        .entity(java.util.Map.of("message", err.getMessage())).build());
    }
}