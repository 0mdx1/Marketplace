package com.ncgroup.marketplaceserver.goods.exceptions;

public class DiscountIsTooHighException extends Exception{
    public DiscountIsTooHighException(String message) {
        super(message);
    }
}
