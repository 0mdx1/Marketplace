package com.ncgroup.marketplaceserver.controller;

import static org.springframework.http.HttpStatus.OK;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ncgroup.marketplaceserver.captcha.service.CaptchaService;
import com.ncgroup.marketplaceserver.exception.domain.CaptchaNotValidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.model.dto.LoginUserDto;
import com.ncgroup.marketplaceserver.model.dto.ResetPasswordDto;
import com.ncgroup.marketplaceserver.model.dto.UserDto;
import com.ncgroup.marketplaceserver.security.constants.JwtConstants;
import com.ncgroup.marketplaceserver.security.model.UserPrincipal;
import com.ncgroup.marketplaceserver.security.util.JwtProvider;
import com.ncgroup.marketplaceserver.service.UserService;

@RequestMapping("/api")
@RestController
@Slf4j
public class UserController  {
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtProvider jwtProvider;
    private CaptchaService captchaService;
    
    @Value("${url.confirm-account.redirect}")
    private String redirectConfirmAccountUrl;
    
    @Value("${url.reset-password.redirect}")
    private String redirectResetPasswordUrl;

    @Value("${url.create-password.redirect}")
    private String redirectCreatePasswordUrl;

    @Value("${security.max-failed-auth}")
    private int maxFailedAuth;
    

    @Autowired
    public UserController(AuthenticationManager authenticationManager, UserService userService, JwtProvider jwtProvider, CaptchaService captchaService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.captchaService = captchaService;
    }
    
    @GetMapping("/userinfo")
    public ResponseEntity<UserDto> getUser(@RequestHeader(value = "Authorization", required = false) String token) {
    	UserDto user = userService.findUserByToken(token);
    	if(user == null) {
    		return new ResponseEntity<UserDto>(HttpStatus.NOT_FOUND);
    	}
    	return new ResponseEntity<UserDto>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> login(@Valid @RequestBody LoginUserDto user,@RequestHeader("captcha-response") String captchaResponse) {
        User loginUser = userService.findUserByEmail(user.getEmail());
        int failedAuth = loginUser.getFailedAuth();
        log.info(failedAuth+"");
        if(failedAuth>=maxFailedAuth&&!captchaService.validateCaptcha(captchaResponse)){
            throw new CaptchaNotValidException("Captcha is not valid");
        }
        authenticate(user.getEmail(), user.getPassword());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(UserDto.convertToDto(loginUser), jwtHeader, OK);
     
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto user) throws MessagingException {
        UserDto newUser = userService.register(
        		user.getName(), user.getSurname(), user.getEmail(), user.getPassword(), user.getPhone(),user.getBirthday());
        return new ResponseEntity<>(newUser, OK);
    }
    
    @GetMapping("/confirm-account")
    public void activate(@RequestParam(name = "token") String link, HttpServletResponse response) throws IOException {
        UserDto newUser = userService.enableUser(link);
        if(newUser != null) {
        	response.setStatus(HttpStatus.OK.value());
        } else {
        	response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        response.sendRedirect(redirectConfirmAccountUrl);
    }

    
    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody JsonNode email) {
        try {
            userService.resetPassword(email.get("email").asText());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.noContent().build();
    }
    
/*    @GetMapping("/confirm-passreset")
    public void confirmPassReset(@RequestParam(name = "token") String link, HttpServletResponse response) throws IOException {
    	User user = userService.getUserByLink(link);
    	if(user != null) {
        	response.setStatus(HttpStatus.OK.value());
        	response.sendRedirect(redirectResetPasswordUrl+"?id="+link);
        } else {
        	response.setStatus(HttpStatus.UNAUTHORIZED.value());
        	response.sendRedirect(redirectResetPasswordUrl);
        }
    }*/
    
    
    @PostMapping("/setnewpassword")
    public ResponseEntity<UserDto> setNewPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        User user = userService.setNewPassword(resetPasswordDto.getLink(), resetPasswordDto.getPassword());
        UserPrincipal userPrincipal = new UserPrincipal(user);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(UserDto.convertToDto(user), jwtHeader, OK);
    }
    
    @GetMapping("/list")
    public ResponseEntity<List<UserDto>> findAllUsers() {
    	List<UserDto> users = userService.getUsers();
        return new ResponseEntity<>(users, OK);
    }

    @PatchMapping("/updateUserRole")
    public ResponseEntity<User> updateRoleUser(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody User user
    ) {
        return new ResponseEntity<>(userService.updateRoleUser(user, token), OK);
    }

    private HttpHeaders getJwtHeader(UserPrincipal user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, JwtConstants.TOKEN_HEADER);
        headers.add(JwtConstants.TOKEN_HEADER, jwtProvider.generateJwtToken(user));
        return headers;
    }

    private void authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
    
    
}