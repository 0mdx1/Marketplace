package com.ncgroup.marketplaceserver.exception.domain;

import com.ncgroup.marketplaceserver.exception.basic.NotValidException;

public class EmailNotValidException extends NotValidException {
	public EmailNotValidException(String message) {
        super(message);
    }
}
