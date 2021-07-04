package com.ncgroup.marketplaceserver.exception.domain;

public class LinkExpiredException extends RuntimeException {
    public LinkExpiredException(String message) {
        super(message);
    }
}
