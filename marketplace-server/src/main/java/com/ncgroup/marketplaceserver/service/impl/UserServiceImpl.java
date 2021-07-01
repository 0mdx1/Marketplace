package com.ncgroup.marketplaceserver.service.impl;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.ncgroup.marketplaceserver.captcha.service.CaptchaService;
import com.ncgroup.marketplaceserver.constants.EmailParam;
import com.ncgroup.marketplaceserver.exception.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ncgroup.marketplaceserver.exception.constants.ExceptionMessage;
import com.ncgroup.marketplaceserver.model.Role;
import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.model.dto.UserDto;
import com.ncgroup.marketplaceserver.repository.UserRepository;
import com.ncgroup.marketplaceserver.security.model.UserPrincipal;
import com.ncgroup.marketplaceserver.security.service.LoginAttemptService;
import com.ncgroup.marketplaceserver.security.util.JwtProvider;
import com.ncgroup.marketplaceserver.service.EmailSenderService;
import com.ncgroup.marketplaceserver.service.UserService;

import lombok.extern.slf4j.Slf4j;

import javax.mail.MessagingException;

@Slf4j
@Service
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {

	private UserRepository userRepository;
	private BCryptPasswordEncoder passwordEncoder;
	private LoginAttemptService loginAttemptService;
	private EmailSenderService emailSenderService;
	private JwtProvider jwtProvider;
	private CaptchaService captchaService;
	
	private final int LINK_VALID_TIME_HOUR = 24;


	@Autowired
	public UserServiceImpl(UserRepository userRepository, 
						   BCryptPasswordEncoder passwordEncoder, 
			               LoginAttemptService loginAttemptService,
			               EmailSenderService emailSenderService,
			               JwtProvider jwtProvider,
			               CaptchaService captchaService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.loginAttemptService = loginAttemptService;
		this.emailSenderService = emailSenderService;
		this.jwtProvider = jwtProvider;
		this.captchaService = captchaService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException(MessageFormat.format(ExceptionMessage.USERNAME_NOT_FOUND, username));
		}

		//validateLoginAttempt(user);
		UserPrincipal userPrincipal = new UserPrincipal(user);
		return userPrincipal;

	}

    public User getCurrentUser() {
	    String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.findUserByEmail(email);
    }

	@Override
	public User updateRoleUser(User user, String token) {
		if(token == null) return null;
		token = token.split(" ")[1];
		String email = jwtProvider.getSubject(token);
		userRepository.updateUserByEmail(user, email);
		return user;
	}

	@Override
    public UserDto findUserByToken(String token) {
    	if(token != null) {
			token = token.split(" ")[1];
			User user = findUserByEmail(jwtProvider.getSubject(token));
			return UserDto.convertToDto(user);
		} else {
			return null;
		}
    }
	

	@Override
	public UserDto register(String name, String surname, String email, String password, String phone, LocalDate birthday) throws MessagingException {
		validateNewEmail(StringUtils.EMPTY, email);
		//validate password
		if(!validatePasswordPattern(password)) {
			throw new PasswordNotValidException(ExceptionMessage.PASSWORD_NOT_VALID);
		}
		
		User user = User.builder()
				.name(name)
				.surname(surname)
				.phone(phone)
				.email(email)
				.birthday(birthday)
				.password(encodePassword(password))
				.lastFailedAuth(LocalDateTime.now())
				.role(Role.ROLE_USER)
				.build();



		String authlink = emailSenderService.sendSimpleEmailValidate(email, name);
		user.setAuthLink(authlink);		
		user = userRepository.save(user);
        
        log.info("New user registered");
        return UserDto.convertToDto(user);
	}

	//Set user.enabled true after user has clicked the correct link sent by email
	@Override
	public UserDto enableUser(String link) {
		User user = validateAuthLink(link);
		userRepository.enableUser(link);
		return user != null ? UserDto.convertToDto(user) : null;
	}
	
	@Override
	public User setNewPassword(String link, String newPassword) {
		User user = validateAuthLink(link);
		validatePasswordPattern(newPassword);
		if(user == null){
			throw new LinkNotValidException("Link is invalid or expired");
		}
		if(user.getPassword() != null && user.getPassword().equals(newPassword)) {
			throw new PasswordNotValidException(ExceptionMessage.SAME_PASSWORD);
		}
		userRepository.updatePassword(user.getEmail(), encodePassword(newPassword));
		return user;
	}
	
	@Override
	public List<UserDto> getUsers() {
		return userRepository.findAll().stream().map(UserDto::convertToDto).collect(Collectors.toList());
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
	public User getUserByLink(String link){
		return validateAuthLink(link);
	}
	
	@Override
	public User addUser(String name, String surname, String email, Role role, String phone) {
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
	public User addUserWithoutCredentials(String name, String surname, String phone) {
		User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setPhone(phone);
        return userRepository.saveWithoutCredentials(user);
	}

	@Override
	public User updateUser(long id, String newName, String newSurname, String newEmail, String newPhone,
			boolean isEnabled) {
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
	public void resetPassword(String email) throws EmailNotFoundException, MessagingException {
		User user = userRepository.findByEmail(email);
		if(user == null) {		
			throw new EmailNotFoundException(MessageFormat.format(ExceptionMessage.USERNAME_NOT_FOUND, email));
		}
		String auth_link = emailSenderService.sendSimpleEmailPasswordRecovery(email, user.getName());
		user.setAuthLink(auth_link);
		userRepository.updateAuthLink(email, auth_link);
	}
	
	/*
	 * This method checks the validity of email so that email is not already taken by another user in case of adding new user
	 * Or in case of updating info about existing user, method returns user associated with given email
	 * If currentEmail is Empty then this method is called from register() or addUser() method
	 * */
	public User validateNewEmail(String currentEmail, String newEmail) {
        //Check that email matches RegExpr
		/*if(!validateEmailPattern(newEmail)) {
			throw new PasswordNotValidException(ExceptionMessage.EMAIL_NOT_VALID);
		}*/
		
		User userByNewEmail = findUserByEmail(newEmail);
        if(StringUtils.isNotBlank(currentEmail)) { //The user wants to update existing email
        	
            User currentUser = findUserByEmail(currentEmail);
            if(currentUser == null) { //No user with such an email
            	throw new UserNotFoundException(MessageFormat.format("User with email {0} does not exist", currentEmail));
            }
            if(userByNewEmail != null && currentUser.getId() != (userByNewEmail.getId())) {
                throw new EmailExistException(MessageFormat.format(ExceptionMessage.EMAIL_ALREADY_EXISTS, currentEmail));
            }
            return currentUser;
        } else {
        	
            if(userByNewEmail != null) {
            	if(!userByNewEmail.isEnabled()) {
            		//user with such email exists but he has not activated account
            		//delete this user as we want to add/register him once more
            		userRepository.deleteById(userByNewEmail.getId());
            	} else {
            		// User with such an email already exists
                	throw new EmailExistException(MessageFormat.format(ExceptionMessage.EMAIL_ALREADY_EXISTS, currentEmail));
            	}
            }
            
            return null;
        }
    }
	
	
	/*private void validateLoginAttempt(User user) {
		if(loginAttemptService.hasExceededMaxAttempts(user.getId())) {
			log.info("Captcha");
			throw new CaptchaNotValidException("Captcha is not valid");
		} else {
			loginAttemptService.successfullLogin(user.getEmail());
		}
		//loginAttemptService.successfullLogin(user.getEmail());
	}*/
	
	//Checks wheather link exists and non-expired
	private User validateAuthLink(String link) {
		User user = userRepository.findByAuthLink(link);
		if(user == null) {
			return null;
		}
		if(user.getAuthLinkDate().isBefore(LocalDateTime.now().minusHours(LINK_VALID_TIME_HOUR))) {
			return null;
		}
		return user;
	}

	public String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}

	
	public boolean validatePasswordPattern(String password) {
		int count = 0;

		   if( 6 <= password.length() && password.length() <= 32  )
		   {
		      if(password.matches(".*[a-z].*")) {
		         count ++;
		      }
		      if( password.matches(".*[A-Z].*") ) {
		         count ++;
		      }
		      if( password.matches(".*[0-9].*") ) {
		         count ++;
		      }
		   }

		   return count >= 3;
	}

}
