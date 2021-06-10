package com.ncgroup.marketplaceserver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiValidationError extends ApiSubError{
    private String field;
    private Object rejectedValue;
    private String message;

    ApiValidationError( String message) {
        this.message = message;
    }
}
