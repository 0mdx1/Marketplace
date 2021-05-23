package com.ncgroup.marketplaceserver.controller;

import static org.springframework.http.HttpStatus.OK;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ncgroup.marketplaceserver.model.Courier;
import com.ncgroup.marketplaceserver.service.CourierService;
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

@RequestMapping("/api/courier")
@RestController
public class CourierController  {
    private AuthenticationManager authenticationManager;
    private CourierService courierService;
    private JwtProvider jwtProvider;

    @Value("${url.confirm-account.redirect}")
    private String redirectConfirmAccountUrl;

    @Value("${url.reset-password.redirect}")
    private String redirectResetPasswordUrl;


    @Autowired
    public CourierController(AuthenticationManager authenticationManager,
                             CourierService courierService, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.courierService = courierService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping()
    public ResponseEntity<UserDto> create (@Valid @RequestBody UserDto courier){
        UserDto newCourier = courierService.create(
                courier.getName(), courier.getSurname(), courier.getEmail(), courier.getPassword(), courier.getPhone());
        return new ResponseEntity<>(newCourier, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Courier> findById(@PathVariable int id) {
        Courier courier = courierService.getById(id);
        return new ResponseEntity<>(courier, OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Courier>> findAll() {
        List<Courier> couriers = courierService.getAll();
        return new ResponseEntity<>(couriers, OK);
    }




}