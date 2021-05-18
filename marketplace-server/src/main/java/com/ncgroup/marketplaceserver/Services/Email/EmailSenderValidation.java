package com.ncgroup.marketplaceserver.Services.Email;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailSenderValidation extends EmailSender {

    //Change to PRIVATE, after the implementation of adding the token to the database
    public static String setActivationToken = UUID.randomUUID().toString();

    //Send activation token to BD
    private void ActivationCodeToBD(){

    }

    @Override
    public void sendSimpleEmail(String toEmail) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("netmark121@gmail.com");
        message.setTo(toEmail);
        message.setText(String.format("Hello! \n" +
                        "Welcome to our Shop! Please visit next link: http://localhost:8080/confirm-account?token=%s", setActivationToken));
        message.setSubject("Activate code");

        mailSender.send(message);
        System.out.println("Mail Send...");
    }
}
