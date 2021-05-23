package com.ncgroup.marketplaceserver.exception.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PasswordNotValidException extends RuntimeException{
	public PasswordNotValidException(String message) {
        super(message);
    }
}
