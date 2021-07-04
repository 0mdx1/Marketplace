package com.ncgroup.marketplaceserver.goods.exceptions;

public class DiscountIsTooHighException extends Exception {
	private static final long serialVersionUID = 1L;

	public DiscountIsTooHighException(String message) {
        super(message);
    }
}
