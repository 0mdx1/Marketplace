package com.ncgroup.marketplaceserver.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ncgroup.marketplaceserver.constants.MailConstants;
import com.ncgroup.marketplaceserver.service.EmailSenderService;


@Service
public class EmailSenderServiceImpl implements EmailSenderService {
    @Value("${url.confirm-account}")
    private String confirmAccountUrl;

    @Value("${url.reset-password}")
    private String resetPasswordUrl;

    @Value("${url.create-password}")
    private String createPasswordUrl;

    @Autowired
    private JavaMailSender mailSender;


    @Override
    public String sendSimpleEmailValidate(String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(MailConstants.SENDER_EMAIL);
        message.setTo(toEmail);
        String generatedToken = generateToken();
        message.setText(String.format(MailConstants.REGISTRATION_MESSAGE + confirmAccountUrl, generatedToken));
        message.setSubject(MailConstants.ACTIVATE_ACCOUNT_SUBJECT);

        mailSender.send(message);
        System.out.println("Mail Send...");
        return generatedToken;

    }

    @Override
    public String sendSimpleEmailPasswordRecovery(String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(MailConstants.SENDER_EMAIL);
        message.setTo(toEmail);
        String generatedToken = generateToken();
        message.setText(String.format(MailConstants.PASSWORD_RECOVERY_MESSAGE + resetPasswordUrl, generatedToken));
        message.setSubject(MailConstants.PASSWORD_RECOVERY_SUBJECT);

        mailSender.send(message);
        System.out.println("Mail Send...");
        return generatedToken;

    }

    @Override
    public String sendSimpleEmailPasswordCreation(String toEmail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(MailConstants.SENDER_EMAIL);
        message.setTo(toEmail);
        String generatedToken = generateToken();
        message.setText(String.format(MailConstants.PASSWORD_CREATION_MESSAGE + createPasswordUrl, generatedToken));
        message.setSubject(MailConstants.PASSWORD_CREATION_SUBJECT);
        mailSender.send(message);
        System.out.println("Mail Send...");
        return generatedToken;

    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

}
