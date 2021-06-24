package com.ncgroup.marketplaceserver.exception.basic;

import com.ncgroup.marketplaceserver.exception.annotation.ApiErrorMetadata;
import org.springframework.http.HttpStatus;

@ApiErrorMetadata(type = "resource-general-1", status = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
