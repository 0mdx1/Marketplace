package com.ncgroup.marketplaceserver.exception.domain;

import com.ncgroup.marketplaceserver.exception.annotation.ApiErrorMetadata;
import com.ncgroup.marketplaceserver.exception.basic.NotFoundException;
import com.ncgroup.marketplaceserver.exception.constants.ExceptionType;
import org.springframework.http.HttpStatus;

@ApiErrorMetadata(type = ExceptionType.EMAIL_NOT_FOUND, status = HttpStatus.BAD_REQUEST)
public class EmailNotFoundException extends NotFoundException {
    public EmailNotFoundException(String message) {
        super(message);
    }
}
