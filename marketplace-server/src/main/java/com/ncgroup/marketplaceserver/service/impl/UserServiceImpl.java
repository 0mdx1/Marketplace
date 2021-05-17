package com.ncgroup.marketplaceserver.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ncgroup.marketplaceserver.exception.domain.EmailExistException;
import com.ncgroup.marketplaceserver.exception.domain.EmailNotFoundException;
import com.ncgroup.marketplaceserver.exception.domain.UserNotFoundException;
import com.ncgroup.marketplaceserver.exception.domain.UsernameExistException;
import com.ncgroup.marketplaceserver.model.Role;
import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.repository.*;
import com.ncgroup.marketplaceserver.security.model.UserPrincipal;
import com.ncgroup.marketplaceserver.security.service.LoginAttemptService;
import com.ncgroup.marketplaceserver.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {

	private UserRepository userRepository;
	private BCryptPasswordEncoder passwordEncoder;
	private LoginAttemptService loginAttemptService;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, LoginAttemptService loginAttemptService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.loginAttemptService = loginAttemptService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("Username does not exist" + username);
		}

		validateLoginAttempt(user);
		// userRepository.save(user);
		UserPrincipal userPrincipal = new UserPrincipal(user);
		log.debug("User was found: " + username);
		return userPrincipal;

	}

	

	@Override
	public User register(String name, String surname, String email, String password)
			throws UserNotFoundException, UsernameExistException, EmailExistException {
		validateNewEmail(StringUtils.EMPTY, email);
		//TODO validate password
		
		User user = User.builder()
				.name(name)
				.surname(surname)
				.email(email)
				.password(encodePassword(password))
				.lastFailedAuth(LocalDateTime.now())
				.role(Role.ROLE_USER)
				.build();
        user = userRepository.save(user);
        log.info("New user registered");
        return user;
	}

	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	

	@Override
	public User findUserById(long id) {
		return userRepository.findById(id);
	}
	
	@Override
	public User addUser(String name, String surname, String email, Role role, String phone) 
			throws UserNotFoundException, UsernameExistException, EmailExistException {
		validateNewEmail(StringUtils.EMPTY, email);
		User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setRole(role);
        user.setPhone(phone);
        userRepository.save(user);
		return user;
	}

	@Override
	public User updateUser(long id, String newName, String newSurname, String newEmail, String newPhone,
			boolean isEnabled) throws UserNotFoundException, UsernameExistException, EmailExistException {
		User user = validateNewEmail(userRepository.findById(id).getEmail(), newEmail);
        user.setName(newName);
        user.setSurname(newSurname);
        user.setEmail(newEmail);
        user.setPhone(newPhone);
        userRepository.save(user);
		return user;
	}

	@Override
	public void deleteUser(long id) {
		userRepository.deleteById(id);
		
	}

	@Override
	public void resetPassword(String email, String newPassword) throws EmailNotFoundException {
		User user = userRepository.findByEmail(email);
		if(user == null) {
			throw new EmailNotFoundException("No user was found by the email");
		}
		user.setPassword(encodePassword(newPassword));
		userRepository.save(user);;
	}
	
	/*
	 * This method checks the validity of email so that email is not already taken by another user in case of adding new user
	 * Or in case of updating info about existing user, method returns user associated with given email
	 * If currentEmail is Empty then this method is called from register() or addUser() method
	 * */
	private User validateNewEmail(String currentEmail, String newEmail) 
			throws UserNotFoundException, UsernameExistException, EmailExistException {
        //TODO Check that email matches RegExpr
		
		User userByNewEmail = findUserByEmail(newEmail);
        if(StringUtils.isNotBlank(currentEmail)) { //The user wants to update existing email
        	
            User currentUser = findUserByEmail(currentEmail);
            if(currentUser == null) { //No user with such an email
                throw new UserNotFoundException("No user with email: " + currentEmail);
            }
            if(userByNewEmail != null && currentUser.getId() != (userByNewEmail.getId())) {
                throw new UsernameExistException("Email already exists");
            }
            return currentUser;
        } else {
        	
            if(userByNewEmail != null) { 
            	// User with such an email already exists
                throw new EmailExistException("Email already exists");
            }
            
            //TODO validate email by sending link
            return null;
        }
    }
	
	
	private void validateLoginAttempt(User user) {
		if(loginAttemptService.hasExceededMaxAttempts(user.getId())) {

			//TODO captcha
		} else {
			loginAttemptService.successfullLogin(user.getEmail());
		}
	}

	private String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}


	
	
}
