package com.ncgroup.marketplaceserver.constants;

public class MailConstants {
	public static final String SENDER_EMAIL = "netmark121@gmail.com";
	public static final String REGISTRATION_LINK = "http://localhost:8080/confirm-account?token=%s";
	public static final String PASSWORD_RECOVERY_LINK = "http://localhost:8080/pass-recovery?token=%s";
	public static final String PASSWORD_CREATION_LINK = "http://localhost:8080/pass-creation?token=%s";
	public static final String REGISTRATION_MESSAGE = "Hello! \n Welcome to our Shop! Please visit next link: ";
	public static final String PASSWORD_RECOVERY_MESSAGE  = "Hello! \n Please visit this link to recover password: ";
	public static final String PASSWORD_CREATION_MESSAGE  = "Hello! \n Please visit this link to create password: ";
	public static final String ACTIVATE_ACCOUNT_SUBJECT = "Activate account";
	public static final String PASSWORD_RECOVERY_SUBJECT = "Password recovery";
	public static final String PASSWORD_CREATION_SUBJECT = "Password creation";
} 
