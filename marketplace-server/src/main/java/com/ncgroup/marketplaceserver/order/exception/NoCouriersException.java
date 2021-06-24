package com.ncgroup.marketplaceserver.order.exception;

import com.ncgroup.marketplaceserver.exception.constants.ExceptionType;
import com.ncgroup.marketplaceserver.exception.annotation.ApiErrorMetadata;
import org.springframework.http.HttpStatus;

@ApiErrorMetadata(type = ExceptionType.NO_COURIERS, status = HttpStatus.NOT_FOUND)
public class NoCouriersException extends RuntimeException {
	public NoCouriersException(String message) {
		super(message);
	}
}
