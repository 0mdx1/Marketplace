package com.ncgroup.marketplaceserver.security.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoginAttemptService {
	private final int MAX_ATTEMPTS_NUMBER = 5;
	private final int LOGIN_FAILURE_TIME_MIN = 15; //time interval in minutes 
	
	private final UserRepository userRepository;
	
	@Autowired
	public LoginAttemptService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	 
	public void successfullLogin(String username) {
		userRepository.updateLastFailedAuth(username, 0);
	}
	
	public void failedLogin(String username) {
		User user = userRepository.findByEmail(username);
		if(user == null) {
			return;
		}
		if(user.getLastFailedAuth() != null &&
				user.getLastFailedAuth().isBefore(LocalDateTime.now().minusMinutes(LOGIN_FAILURE_TIME_MIN))) {
			// The amount of time after which user can login again has passed
			userRepository.updateLastFailedAuth(username, 1);
		} else {
			userRepository.updateLastFailedAuth(username, user.getFailedAuth()+1);
		}
		
	}
	
	public boolean hasExceededMaxAttempts(long userId) {
		User user = userRepository.findById(userId);
		if(user.getLastFailedAuth() != null &&
				user.getLastFailedAuth().isBefore(LocalDateTime.now().minusMinutes(LOGIN_FAILURE_TIME_MIN))) {
			return false;
		}
		return user.getFailedAuth() >= MAX_ATTEMPTS_NUMBER;
	}
}
