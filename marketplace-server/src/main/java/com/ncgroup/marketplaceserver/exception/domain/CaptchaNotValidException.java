package com.ncgroup.marketplaceserver.exception.domain;

import com.ncgroup.marketplaceserver.exception.basic.NotValidException;

public class CaptchaNotValidException extends NotValidException {
    public CaptchaNotValidException(String message) {
        super(message);
    }
}
