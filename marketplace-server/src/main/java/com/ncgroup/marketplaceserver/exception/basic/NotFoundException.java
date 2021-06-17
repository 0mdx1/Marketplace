package com.ncgroup.marketplaceserver.exception.basic;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
