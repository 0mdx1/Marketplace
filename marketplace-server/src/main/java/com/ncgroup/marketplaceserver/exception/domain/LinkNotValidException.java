package com.ncgroup.marketplaceserver.exception.domain;

import com.ncgroup.marketplaceserver.exception.annotation.ApiErrorMetadata;
import com.ncgroup.marketplaceserver.exception.basic.NotValidException;
import com.ncgroup.marketplaceserver.exception.constants.ExceptionType;
import org.springframework.http.HttpStatus;

@ApiErrorMetadata(type = ExceptionType.LINK_NOT_VALID, status = HttpStatus.BAD_REQUEST)
public class LinkNotValidException extends NotValidException {
	public LinkNotValidException(String message) {
        super(message);
    }
}
