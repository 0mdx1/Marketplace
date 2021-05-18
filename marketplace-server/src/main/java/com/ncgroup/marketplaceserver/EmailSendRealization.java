package com.ncgroup.marketplaceserver;


import com.ncgroup.marketplaceserver.Services.Email.EmailSenderValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class EmailSendRealization {


    @Autowired
    private EmailSenderValidation service;

    @EventListener(ApplicationReadyEvent.class)
    public void triggerMail() {
        service.sendSimpleEmail("TESTEMAIL@gmail.com");
    }
}
