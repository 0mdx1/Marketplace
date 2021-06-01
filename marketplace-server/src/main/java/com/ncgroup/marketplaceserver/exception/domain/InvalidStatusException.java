package com.ncgroup.marketplaceserver.exception.domain;

public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException(String message) {
        super(message);
    }
}
