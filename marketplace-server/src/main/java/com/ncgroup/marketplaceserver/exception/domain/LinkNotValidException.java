package com.ncgroup.marketplaceserver.exception.domain;

import com.ncgroup.marketplaceserver.exception.basic.NotValidException;

public class LinkNotValidException extends NotValidException {
	public LinkNotValidException(String message) {
        super(message);
    }
}
