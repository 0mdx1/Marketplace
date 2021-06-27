package com.ncgroup.marketplaceserver.exception.domain;

import com.ncgroup.marketplaceserver.exception.annotation.ApiErrorMetadata;
import com.ncgroup.marketplaceserver.exception.basic.AlreadyExistException;
import com.ncgroup.marketplaceserver.exception.constants.ExceptionType;
import org.springframework.http.HttpStatus;

@ApiErrorMetadata(type = ExceptionType.EMAIL_ALREADY_EXISTS, status = HttpStatus.BAD_REQUEST)
public class EmailExistException extends AlreadyExistException {
    public EmailExistException(String message) {
        super(message);
    }
}
