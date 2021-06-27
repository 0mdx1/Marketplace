package com.ncgroup.marketplaceserver.exception.domain;

import com.ncgroup.marketplaceserver.exception.annotation.ApiErrorMetadata;
import com.ncgroup.marketplaceserver.exception.basic.NotValidException;
import com.ncgroup.marketplaceserver.exception.constants.ExceptionType;
import org.springframework.http.HttpStatus;

@ApiErrorMetadata(type = ExceptionType.EMAIL_INVALID, status = HttpStatus.BAD_REQUEST)
public class EmailNotValidException extends NotValidException {
	public EmailNotValidException(String message) {
        super(message);
    }
}
