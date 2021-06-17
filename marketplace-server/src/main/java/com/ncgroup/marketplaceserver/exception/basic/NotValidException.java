package com.ncgroup.marketplaceserver.exception.basic;

public class NotValidException extends RuntimeException{
    public NotValidException(String message) {
        super(message);
    }
}
