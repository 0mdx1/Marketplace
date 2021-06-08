package com.ncgroup.marketplaceserver.service.impl;

import java.util.UUID;

import com.ncgroup.marketplaceserver.constants.EmailParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import com.ncgroup.marketplaceserver.constants.MailConstants;
import com.ncgroup.marketplaceserver.service.EmailSenderService;

import javax.mail.MessagingException;


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

    @Autowired
    private TemplateEngine templateEngine;


    @Override
    public String sendSimpleEmailValidate(String toEmail) throws MessagingException {
        javax.mail.internet.MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
        EmailParam emailParam = new EmailParam();

        message.setFrom(MailConstants.SENDER_EMAIL);
        message.setTo(toEmail);
        message.setSubject(MailConstants.ACTIVATE_ACCOUNT_SUBJECT);

        emailParam.setMess(String.format(MailConstants.REGISTRATION_MESSAGE));
        emailParam.setLink(String.format(confirmAccountUrl, emailParam.getToken()));

        Context context = new Context();
        context.setVariable("EmailParam", emailParam);

        String html = templateEngine.process("EmailValidation", context);
        message.setText(html, true);
        mailSender.send(mimeMessage);
        return emailParam.getToken();

    }

    @Override
    public String sendSimpleEmailPasswordRecovery(String toEmail) throws MessagingException {
        javax.mail.internet.MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
        EmailParam emailParam = new EmailParam();

        message.setFrom(MailConstants.SENDER_EMAIL);
        message.setTo(toEmail);
        message.setSubject(MailConstants.PASSWORD_RECOVERY_SUBJECT);

        emailParam.setMess(String.format(MailConstants.PASSWORD_RECOVERY_MESSAGE));
        emailParam.setLink(String.format(resetPasswordUrl, emailParam.getToken()));

        Context context = new Context();
        context.setVariable("EmailParam", emailParam);

        String html = templateEngine.process("EmailValidation", context);
        message.setText(html, true);
        mailSender.send(mimeMessage);
        return emailParam.getToken();

    }

    @Override
    public String sendSimpleEmailPasswordCreation(String toEmail) {
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
    private String generateToken(){
        return UUID.randomUUID().toString();
    }
}
