package com.hitss.product.domain.exception;

import com.hitss.shared.domain.exception.DomainException;

public class ProductNotFoundException extends DomainException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}