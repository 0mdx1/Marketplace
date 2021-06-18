package com.ncgroup.marketplaceserver.exception.basic;

public class AlreadyExistException extends RuntimeException {
    public AlreadyExistException(String message) {
        super(message);
    }
}
