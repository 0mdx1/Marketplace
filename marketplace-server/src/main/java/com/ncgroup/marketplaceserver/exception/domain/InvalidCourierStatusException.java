package com.ncgroup.marketplaceserver.exception.domain;

public class InvalidCourierStatusException extends RuntimeException {
    public InvalidCourierStatusException(String message) {
        super(message);
    }
}
