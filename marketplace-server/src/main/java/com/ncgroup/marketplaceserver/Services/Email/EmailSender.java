package com.ncgroup.marketplaceserver.Services.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public abstract class EmailSender {

    @Autowired
    protected JavaMailSender mailSender;

    public abstract void sendSimpleEmail(String toEmail);
}
