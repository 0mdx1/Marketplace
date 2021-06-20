package com.ncgroup.marketplaceserver.captcha.service;

public interface CaptchaService {
    boolean validateCaptcha(String captchaResponse);
}
