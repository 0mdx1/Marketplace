package com.ncgroup.marketplaceserver.Services.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderPassRecovery extends EmailSender{

    @Override
    public void sendSimpleEmail(String toEmail) {



        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("netmark121@gmail.com");
        message.setTo(toEmail);
        message.setText(String.format("Hello! \n" +
                " Please visit this link to recovery password: http://localhost:8080/pass-recovery"));
        message.setSubject("Password recovery");

        mailSender.send(message);
        System.out.println("Mail Send...");

    }
}
