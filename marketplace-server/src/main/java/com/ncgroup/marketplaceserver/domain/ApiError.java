package com.ncgroup.marketplaceserver.domain;

import lombok.Data;
import java.time.Instant;

@Data
public class ApiError {

    private String type;
    private String message;
    private Instant dateTime;

    public ApiError(String type, String message) {
        this.type = type;
        this.message = message;
        this.dateTime = Instant.now();
    }

    //    private HttpStatus status;
//    private LocalDateTime timestamp;
//    private String message;
//    private String debugMessage;
//    private String errorType;
//    private List<ApiSubError> subErrors;
//
//    private ApiError() {
//        timestamp = LocalDateTime.now();
//    }
//
//    public ApiError(HttpStatus status) {
//        this();
//        this.status = status;
//    }
//
//    public ApiError(HttpStatus status, String message, Throwable ex) {
//        this();
//        this.status = status;
//        this.message = message;
//        this.debugMessage = ex.getLocalizedMessage();
//    }
//
//    private void addSubError(ApiSubError subError) {
//        if (subErrors == null) {
//            subErrors = new ArrayList<>();
//        }
//        subErrors.add(subError);
//    }
//
//    public void addValidationError( String field, Object rejectedValue, String message) {
//        addSubError(new ApiValidationError( field, rejectedValue, message));
//    }
//
//    private void addValidationError(ConstraintViolation<?> cv) {
//        this.addValidationError(
//                ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
//                cv.getInvalidValue(),
//                cv.getMessage());
//    }
//
//    public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
//        constraintViolations.forEach(this::addValidationError);
//    }
}
