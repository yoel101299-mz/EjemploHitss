package com.hitss.product.infrastructure.outbound.mapper;

import com.hitss.product.domain.model.Product;
import com.hitss.product.infrastructure.outbound.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface ProductEntityMapper {
    Product toDomain(ProductEntity entity);
    ProductEntity toEntity(Product domain);
    List<Product> toDomainList(List<ProductEntity> entities);
}