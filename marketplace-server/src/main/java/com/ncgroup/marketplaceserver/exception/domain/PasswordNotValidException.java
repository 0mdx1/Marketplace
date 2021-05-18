package com.ncgroup.marketplaceserver.exception.domain;

public class PasswordNotValidException extends RuntimeException{
	public PasswordNotValidException(String message) {
        super(message);
    }
}
