package com.ncgroup.marketplaceserver.exception.domain;

import com.ncgroup.marketplaceserver.exception.basic.NotValidException;
import com.ncgroup.marketplaceserver.exception.constants.ExceptionType;
import com.ncgroup.marketplaceserver.exception.annotation.ApiErrorMetadata;
import org.springframework.http.HttpStatus;

@ApiErrorMetadata(type = ExceptionType.CAPTCHA_INVALID, status = HttpStatus.BAD_REQUEST)
public class CaptchaNotValidException extends NotValidException {
    public CaptchaNotValidException(String message) {
        super(message);
    }
}
