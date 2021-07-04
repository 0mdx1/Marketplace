package com.ncgroup.marketplaceserver.goods.exceptions;

public class GoodAlreadyExistsException extends Exception {
	private static final long serialVersionUID = 1L;

	public GoodAlreadyExistsException(String message) {
        super(message);
    }
}
