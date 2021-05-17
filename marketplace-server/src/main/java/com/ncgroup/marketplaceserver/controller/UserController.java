package com.ncgroup.marketplaceserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ncgroup.marketplaceserver.exception.domain.EmailExistException;
import com.ncgroup.marketplaceserver.exception.domain.EmailNotFoundException;
import com.ncgroup.marketplaceserver.exception.domain.UserNotFoundException;
import com.ncgroup.marketplaceserver.exception.domain.UsernameExistException;
import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.security.constants.JwtConstants;
import com.ncgroup.marketplaceserver.security.model.UserPrincipal;
import com.ncgroup.marketplaceserver.security.util.JwtProvider;
import com.ncgroup.marketplaceserver.service.UserService;

import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = { "/", "/user"})
public class UserController  {
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtProvider jwtProvider;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, UserService userService, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
    	try {
    	log.info("Before authentication");
        authenticate(user.getEmail(), user.getPassword());
        log.info("After authentication");
    	} catch(Throwable e) {
    		e.printStackTrace();
    	}
        User loginUser = userService.findUserByEmail(user.getEmail());
        log.info("Login user was found");
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(loginUser, jwtHeader, OK);
     
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) 
    		throws UserNotFoundException, UsernameExistException, EmailExistException {
        User newUser = userService.register(user.getName(), user.getSurname(), user.getEmail(), user.getPassword());
        return new ResponseEntity<>(newUser, OK);
    }
    
    @PostMapping("/resetpassword")
    public ResponseEntity<Void> resetPassword(@RequestBody User user) 
    		throws EmailExistException, EmailNotFoundException {
        userService.resetPassword(user.getEmail(), user.getPassword());
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) 
    		throws UserNotFoundException, UsernameExistException, EmailExistException {
        User newUser = userService.addUser(user.getName(),user.getSurname(), user.getEmail(), user.getRole(), user.getPassword());
        return new ResponseEntity<>(newUser, OK);
    }
    
    @GetMapping("/find/{id}")
    public ResponseEntity<User> addUser(@PathVariable long id) {
        User user = userService.findUserById(id);
        return new ResponseEntity<>(user, OK);
    }
    
    @GetMapping("/list")
    public ResponseEntity<List<User>> findAllUsers() {
    	List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, OK);
    }
    
    @PostMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) 
    		throws UserNotFoundException, UsernameExistException, EmailExistException {
        User updateUser = userService.updateUser(
        		user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getPhone(), user.isEnabled())
;        return new ResponseEntity<>(updateUser, OK);
    }

    private HttpHeaders getJwtHeader(UserPrincipal user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtConstants.TOKEN_HEADER, jwtProvider.generateJwtToken(user));
        return headers;
    }

    private void authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}