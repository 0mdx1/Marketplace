package com.ncgroup.marketplaceserver.exception.domain;

import com.ncgroup.marketplaceserver.exception.basic.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class EmailNotFoundException extends NotFoundException {
    public EmailNotFoundException(String message) {
        super(message);
    }
}
