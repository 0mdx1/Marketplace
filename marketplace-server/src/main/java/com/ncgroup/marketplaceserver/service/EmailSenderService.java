package com.ncgroup.marketplaceserver.service;


import com.ncgroup.marketplaceserver.constants.EmailParam;
import javax.mail.MessagingException;

public interface EmailSenderService {
	String sendSimpleEmailValidate(String toEmail) throws MessagingException;
	String sendSimpleEmailPasswordRecovery(String toEmail) throws MessagingException;
	String sendSimpleEmailPasswordCreation(String toEmail);
}
