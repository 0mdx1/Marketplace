package com.ncgroup.marketplaceserver.exception.domain;

import com.ncgroup.marketplaceserver.exception.basic.AlreadyExistException;

public class EmailExistException extends AlreadyExistException {
    public EmailExistException(String message) {
        super(message);
    }
}
