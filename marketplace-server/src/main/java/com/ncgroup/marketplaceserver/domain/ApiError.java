package com.ncgroup.marketplaceserver.domain;

import lombok.Data;
import java.time.Instant;

@Data
public class ApiError {

    private String type;
    private String message;
    private Instant timestamp;

    public ApiError(String type, String message) {
        this.type = type;
        this.message = message;
        this.timestamp = Instant.now();
    }
}
