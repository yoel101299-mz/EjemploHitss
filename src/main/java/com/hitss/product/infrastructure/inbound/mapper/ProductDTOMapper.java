package com.hitss.product.infrastructure.inbound.mapper;

import com.hitss.product.domain.model.Product;
import com.hitss.product.infrastructure.inbound.dto.ProductResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface ProductDTOMapper {
    Product toDomain(ProductRequestDTO dto);
    ProductResponseDTO toResponse(Product domain);
    List<ProductResponseDTO> toResponseList(List<Product> domainList);
}