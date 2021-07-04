package com.ncgroup.marketplaceserver.order.exception;

public class NoCouriersException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NoCouriersException(String message) {
        super(message);
    }
}
