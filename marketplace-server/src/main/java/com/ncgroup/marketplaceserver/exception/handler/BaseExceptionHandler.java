package com.ncgroup.marketplaceserver.exception.handler;

import com.ncgroup.marketplaceserver.domain.ApiError;
import com.ncgroup.marketplaceserver.exception.constants.ExceptionType;
import com.ncgroup.marketplaceserver.exception.annotation.ApiErrorMetadata;

import com.ncgroup.marketplaceserver.file.UnsupportedContentTypeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        if(body==null){
        	ex.printStackTrace();
            ApiError apiError = new ApiError(ExceptionType.UNEXPECTED_ERROR,"Unexpected error occurred");
            return new ResponseEntity<>(apiError,headers,status);
        }
        return new ResponseEntity<>(body,headers,status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        String message = builder.substring(0, builder.length() - 2);
        ApiError apiError = new ApiError(ExceptionType.UNSUPPORTED_MEDIA,message);
        return this.handleExceptionInternal(ex,apiError,headers,status,request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        String message = this.generateMessageForFieldError(ex.getBindingResult().getFieldErrors().get(0));
        ApiError apiError = new ApiError(ExceptionType.VALIDATION_FAILED,message);
        return this.handleExceptionInternal(ex,apiError,headers,status,request);
    }

    private String generateMessageForFieldError(FieldError fieldError){
        return String.format("Validation failed for %s=%s. %s",
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
    }

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleSizeLimitExceededException(MaxUploadSizeExceededException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        String message = String.format("Maximum upload size of %s exceeded", maxFileSize);
        ApiError apiError = new ApiError(ExceptionType.FILE_SIZE_EXCEEDED,message);
        return this.handleExceptionInternal(ex,apiError,headers,HttpStatus.BAD_REQUEST,request);
    }

    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        String message = "Invalid sort parameter";
        ApiError apiError = new ApiError(ExceptionType.BAD_SORT_PARAMETER, message);
        return this.handleExceptionInternal(ex,apiError,headers,HttpStatus.BAD_REQUEST,request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        String message = ex.getConstraintViolations().iterator().next().getMessage();
        ApiError apiError = new ApiError(ExceptionType.VALIDATION_FAILED,message);
        return this.handleExceptionInternal(ex,apiError,headers,HttpStatus.BAD_REQUEST,request);
    }

    @ExceptionHandler(UnsupportedContentTypeException.class)
    public ResponseEntity<Object> handleUnsupportedContentTypeException(UnsupportedContentTypeException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" content type is not supported. Supported content types for a file are ");
        ex.getSupportedContentTypes().forEach(t -> builder.append(t).append(", "));
        String message = builder.substring(0, builder.length() - 2);
        ApiError apiError = new ApiError(ExceptionType.UNSUPPORTED_MEDIA,message);
        return this.handleExceptionInternal(ex,apiError,headers,HttpStatus.UNSUPPORTED_MEDIA_TYPE,request);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest request){
        HttpHeaders headers = new HttpHeaders();
        ApiError apiError = new ApiError(ExceptionType.BAD_CREDENTIALS,ex.getMessage());
        return this.handleExceptionInternal(ex, apiError, headers,HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request){
        HttpHeaders headers = new HttpHeaders();
        Class<?> clazz = ex.getClass();
        if (clazz.isAnnotationPresent(ApiErrorMetadata.class)) {
            String type = clazz.getAnnotation(ApiErrorMetadata.class).type();
            HttpStatus status = clazz.getAnnotation(ApiErrorMetadata.class).status();
            ApiError apiError = new ApiError(type,ex.getMessage());
            return this.handleExceptionInternal(ex,apiError,headers,status,request);
        }
        return this.handleExceptionInternal(ex, null, headers,HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
