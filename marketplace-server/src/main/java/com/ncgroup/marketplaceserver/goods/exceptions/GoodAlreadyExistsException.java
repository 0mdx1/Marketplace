package com.ncgroup.marketplaceserver.goods.exceptions;

public class GoodAlreadyExistsException extends Exception {
    public GoodAlreadyExistsException(String message) {
        super(message);
    }
}
