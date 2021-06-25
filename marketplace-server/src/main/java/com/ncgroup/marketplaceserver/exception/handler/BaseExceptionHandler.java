package com.ncgroup.marketplaceserver.exception.handler;

import com.ncgroup.marketplaceserver.domain.ApiError;
import com.ncgroup.marketplaceserver.exception.basic.NotFoundException;
import com.ncgroup.marketplaceserver.exception.domain.CaptchaNotValidException;
import com.ncgroup.marketplaceserver.order.exception.NoCouriersException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

    private final static String FIELD_VALIDATION_ERROR = "FIELD_VALIDATION_ERROR";
    private final static String CAPTCHA_VALIDATION_ERROR = "CAPTCHA_VALIDATION_ERROR";
    private final static String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotFoundException(Exception ex, WebRequest request){
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(),ex);
        return super.handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Validation error");
        apiError.setErrorType(FIELD_VALIDATION_ERROR);
        apiError.addValidationErrors(ex.getConstraintViolations());
        return super.handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), request);
    }
    
    @ExceptionHandler(NoCouriersException.class)
    protected ResponseEntity<Object> handleNoCouriersException(Exception ex, WebRequest request){
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND,ex.getMessage(),ex);
        return super.handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), request);
    }

    @ExceptionHandler(CaptchaNotValidException.class)
    protected ResponseEntity<Object> handleCaptchaNotValidException(CaptchaNotValidException ex, WebRequest request){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,ex.getMessage(),ex);
        apiError.setErrorType(CAPTCHA_VALIDATION_ERROR);
        return super.handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), request);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleInternalException(Exception ex, WebRequest request){
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage(),ex);
        apiError.setErrorType(INTERNAL_SERVER_ERROR);
        return super.handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), request);
    }
}
