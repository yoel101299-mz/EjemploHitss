package com.hitss.product.aplication.port;

import com.hitss.product.aplication.dto.CreateProductCommand;
import com.hitss.product.aplication.dto.ProductDetailsDTO;
import io.smallrye.mutiny.Uni;

public interface CreateProductUseCase {
    Uni<ProductDetailsDTO> create(CreateProductCommand createProductCommand);
}
