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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.TextNode;
import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.model.dto.LoginUserDto;
import com.ncgroup.marketplaceserver.model.dto.UserDto;
import com.ncgroup.marketplaceserver.security.constants.JwtConstants;
import com.ncgroup.marketplaceserver.security.model.UserPrincipal;
import com.ncgroup.marketplaceserver.security.util.JwtProvider;
import com.ncgroup.marketplaceserver.service.UserService;

import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import javax.validation.Valid;

@Slf4j
@RestController
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
    public ResponseEntity<UserDto> login(@Valid @RequestBody LoginUserDto user) {
    	authenticate(user.getEmail(), user.getPassword());
        User loginUser = userService.findUserByEmail(user.getEmail());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(UserDto.convertToDto(loginUser), jwtHeader, OK);
     
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto user) {
        UserDto newUser = userService.register(
        		user.getName(), user.getSurname(), user.getEmail(), user.getPassword(), user.getPhone());
        return new ResponseEntity<>(newUser, OK);
    }
    
    @GetMapping("/confirm-account")
    public ResponseEntity<UserDto> activate(@RequestParam(name = "token") String link) {
        UserDto newUser = userService.enableUser(link);
        return new ResponseEntity<>(newUser, OK);
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody TextNode email) {
        userService.resetPassword(email.asText());
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/setnewpassword/{link}")
    public ResponseEntity<UserDto> setNewPassword(@RequestBody String password, @PathVariable String link) {
        userService.setNewPassword(link, password);
        return ResponseEntity.noContent().build();
    }
    
    /*@PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User newUser = userService.addUser(user.getName(),user.getSurname(), user.getEmail(), user.getRole(), user.getPassword());
        return new ResponseEntity<>(newUser, OK);
    }
    
    @GetMapping("/find/{id}")
    public ResponseEntity<User> addUser(@PathVariable long id) {
        User user = userService.findUserById(id);
        return new ResponseEntity<>(user, OK);
    }
    
    @GetMapping("/list")
    public ResponseEntity<List<UserDto>> findAllUsers() {
    	List<UserDto> users = userService.getUsers();
        return new ResponseEntity<>(users, OK);
    }
    
    @PostMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updateUser = userService.updateUser(
        		user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getPhone(), user.isEnabled())
;        return new ResponseEntity<>(updateUser, OK);
    }*/

    private HttpHeaders getJwtHeader(UserPrincipal user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtConstants.TOKEN_HEADER, jwtProvider.generateJwtToken(user));
        return headers;
    }

    private void authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}