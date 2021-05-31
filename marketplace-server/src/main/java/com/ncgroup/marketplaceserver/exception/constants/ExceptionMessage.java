package com.ncgroup.marketplaceserver.exception.constants;

public class ExceptionMessage {
	public static final String PASSWORD_NOT_VALID = 
			"Password has to contain at least one capital letter, lower case letter and number";
	public static final String EMAIL_NOT_VALID = "Email is not valid";
	public static final String USERNAME_NOT_FOUND = "User with email {0} does not exist";
	public static final String EMAIL_ALREADY_EXISTS = "Email {0} already exists";
	public static final String TOKEN_NOT_VERIFIED = "Token is not verified";
	public static final String LINK_NOT_VALID = "Link is not valid";
	public static final String LINK_EXPIRED = "Link has already expired";
	public static final String SAME_PASSWORD = "New password cannot be the same as an old one";
	public static final String INVALID_COURIER_STATUS = "Status must be active, inactive or terminated";
	public static final String INVALID_MANAGER_STATUS = "Status must be active or terminated";
}
