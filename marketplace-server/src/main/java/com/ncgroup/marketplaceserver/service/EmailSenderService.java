package com.ncgroup.marketplaceserver.service;


public interface EmailSenderService {
	String sendSimpleEmailValidate(String toEmail);
	String sendSimpleEmailPasswordRecovery(String toEmail);
	String sendSimpleEmailPasswordCreation(String toEmail);
}
