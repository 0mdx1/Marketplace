package com.ncgroup.marketplaceserver.exception.domain;

import com.ncgroup.marketplaceserver.exception.annotation.ApiErrorMetadata;
import com.ncgroup.marketplaceserver.exception.constants.ExceptionType;
import org.springframework.http.HttpStatus;

@ApiErrorMetadata(type = ExceptionType.STATUS_INVALID, status = HttpStatus.BAD_REQUEST)
public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException(String message) {
        super(message);
    }
}
