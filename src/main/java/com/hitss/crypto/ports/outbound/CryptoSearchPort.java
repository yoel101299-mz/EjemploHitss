package com.hitss.crypto.ports.outbound;

import com.hitss.crypto.domain.model.CryptoPrice;
import io.smallrye.mutiny.Uni;

public interface CryptoSearchPort {
    Uni<CryptoPrice> fetchPrice(String symbol);
}