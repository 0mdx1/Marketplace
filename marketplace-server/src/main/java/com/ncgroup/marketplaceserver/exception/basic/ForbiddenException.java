package com.ncgroup.marketplaceserver.exception.basic;

public class ForbiddenException extends RuntimeException{
    public ForbiddenException(String message) {
        super(message);
    }
}
