package com.ncgroup.marketplaceserver.exception.constants;

public final class ExceptionType {
    public static final String CAPTCHA_INVALID = "validation-captcha-1";
    public static final String EMAIL_ALREADY_EXISTS = "validation-email-1";
    public static final String EMAIL_NOT_FOUND = "validation-email-2";
    public static final String EMAIL_INVALID = "validation-email-3";
    public static final String STATUS_INVALID = "validation-status-1";
    public static final String PASSWORD_INVALID = "validation-password-1";
    public static final String NO_COURIERS = "resource-courier-1";
    public static final String USER_NOT_FOUND = "resource-user-1";
    public static final String UNEXPECTED_ERROR = "general-0";
    public static final String UNSUPPORTED_MEDIA = "general-1";
    public static final String VALIDATION_FAILED = "general-2";
    public static final String RESOURCE_NOT_FOUND = "general-3";
    public static final String FILE_SIZE_EXCEEDED = "general-4";
    public static final String LINK_NOT_VALID = "general-5";
    public static final String BAD_SORT_PARAMETER = "general-6";
    public static final String UNAUTHENTICATED = "auth-1";
    public static final String ACCESS_DENIED = "auth-2";
    public static final String BAD_CREDENTIALS = "auth-3";
    private ExceptionType(){}
}
