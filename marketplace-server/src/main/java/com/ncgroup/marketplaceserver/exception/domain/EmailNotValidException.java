package com.ncgroup.marketplaceserver.exception.domain;

public class EmailNotValidException extends RuntimeException{
	public EmailNotValidException(String message) {
        super(message);
    }
}
