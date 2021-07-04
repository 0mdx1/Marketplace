package com.ncgroup.marketplaceserver.service;

import javax.mail.MessagingException;

public interface EmailSenderService {
    String sendSimpleEmailValidate(String toEmail, String name) throws MessagingException;

    String sendSimpleEmailPasswordRecovery(String toEmail, String name) throws MessagingException;

    String sendSimpleEmailPasswordCreation(String toEmail, String name) throws MessagingException;
}
