package com.ncgroup.marketplaceserver.security.listener;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.ncgroup.marketplaceserver.security.model.UserPrincipal;
import com.ncgroup.marketplaceserver.security.service.LoginAttemptService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthenticationSuccessListener {
	private final LoginAttemptService loginAttemptService;
	
	public AuthenticationSuccessListener(LoginAttemptService loginAttemptService) {
		this.loginAttemptService = loginAttemptService;
	}
	
	@EventListener
	public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
		Object principal = event.getAuthentication().getPrincipal();
		if(principal instanceof UserPrincipal) {
			UserPrincipal user = (UserPrincipal) principal;
			String username = user.getUsername();
			loginAttemptService.successfullLogin(username);
		}
	}
}