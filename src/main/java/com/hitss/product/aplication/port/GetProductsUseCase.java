package com.hitss.product.aplication.port;

import com.hitss.product.aplication.dto.ProductDetailsDTO;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface GetProductsUseCase {
    Uni<List<ProductDetailsDTO>> getAll();
}
