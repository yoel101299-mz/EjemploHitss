package com.hitss.product.aplication.dto.mapper;

import com.hitss.product.aplication.dto.ProductDetailsDTO;
import com.hitss.product.domain.model.Money;
import com.hitss.product.domain.model.Product;
import com.hitss.product.domain.model.ProductId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface ProductDetailsMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "price", source = "price")
    ProductDetailsDTO toDto(Product product);
    List<ProductDetailsDTO> toDtoList(List<Product> products);

    default String map(ProductId id) {
        return id.value();
    }

    default Double map(Money price) {
        return price.getAmount().doubleValue();
    }
}