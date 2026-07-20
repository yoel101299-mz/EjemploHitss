package com.hitss.crypto.ports.inbound;

import com.hitss.crypto.domain.model.CryptoPrice;
import io.smallrye.mutiny.Uni;

public interface GetCryptoPriceUseCase {
    Uni<CryptoPrice> execute(String symbol);
}