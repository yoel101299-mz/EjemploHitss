package com.hitss.product.aplication.port;

import com.hitss.product.aplication.dto.ProductDetailsDTO;
import com.hitss.product.aplication.dto.UpdateProductCommand;
import io.smallrye.mutiny.Uni;

public interface UpdateProductUseCase {
    Uni<ProductDetailsDTO> update(UpdateProductCommand updateProductCommand);
}
