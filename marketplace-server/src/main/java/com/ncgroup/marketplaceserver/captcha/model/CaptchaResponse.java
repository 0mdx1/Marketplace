package com.ncgroup.marketplaceserver.captcha.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class CaptchaResponse {
    private Boolean success;
    private Date timestamp;
    private String hostname;
    private List<String> errorCodes;
}
