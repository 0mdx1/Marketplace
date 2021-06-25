package com.ncgroup.marketplaceserver.exception.domain;

import com.ncgroup.marketplaceserver.exception.annotation.ApiErrorMetadata;
import com.ncgroup.marketplaceserver.exception.basic.NotValidException;
import com.ncgroup.marketplaceserver.exception.constants.ExceptionType;
import org.springframework.http.HttpStatus;

@ApiErrorMetadata(type = ExceptionType.PASSWORD_INVALID, status = HttpStatus.BAD_REQUEST)
public class PasswordNotValidException extends NotValidException {
	public PasswordNotValidException(String message) {
        super(message);
    }
}
