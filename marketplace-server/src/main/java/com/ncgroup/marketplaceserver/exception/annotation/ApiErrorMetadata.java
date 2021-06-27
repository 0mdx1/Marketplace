package com.ncgroup.marketplaceserver.exception.annotation;

import org.springframework.http.HttpStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ApiErrorMetadata {
    public String type() default "general-1";
    public HttpStatus status() default HttpStatus.INTERNAL_SERVER_ERROR;
}
