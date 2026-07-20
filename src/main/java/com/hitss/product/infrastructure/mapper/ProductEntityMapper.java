package com.hitss.product.infrastructure.mapper;

import com.hitss.product.domain.model.Money;
import com.hitss.product.domain.model.Product;
import com.hitss.product.domain.model.ProductId;
import com.hitss.product.infrastructure.persistence.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface ProductEntityMapper {

    Product toDomain(ProductEntity entity);

    ProductEntity toEntity(Product product);

    List<Product> toDomainList(List<ProductEntity> productEntities);

    default ProductId map(String value) {
        return ProductId.of(value);
    }

    default String map(ProductId id) {
        return id.value();
    }

    default Money map(BigDecimal value) {
        return Money.of(value.doubleValue());
    }

    default BigDecimal map(Money money) {
        return money.getAmount();
    }
}