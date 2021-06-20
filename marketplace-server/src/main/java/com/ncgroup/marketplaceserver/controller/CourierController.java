package com.ncgroup.marketplaceserver.controller;

import static org.springframework.http.HttpStatus.OK;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import com.ncgroup.marketplaceserver.constants.StatusConstants;
import com.ncgroup.marketplaceserver.model.Courier;
import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.model.dto.CourierDto;
import com.ncgroup.marketplaceserver.model.dto.CourierUpdateDto;
import com.ncgroup.marketplaceserver.service.CourierService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import com.ncgroup.marketplaceserver.model.dto.UserDto;
import com.ncgroup.marketplaceserver.security.util.JwtProvider;

@RequestMapping("/api/courier")
@RestController
@Slf4j
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
    public ResponseEntity<UserDto> create (@Valid @RequestBody CourierDto courier){
    	log.info(courier.toString());
        UserDto newCourier = courierService.save(
                courier.getName(), courier.getSurname(), courier.getEmail(),
                courier.getPhone(), courier.getBirthday(), courier.getStatus());
        return new ResponseEntity<>(newCourier, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable int id) {
        return new ResponseEntity<>(UserDto.convertToDto(courierService.getById(id)), OK);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<CourierUpdateDto> updateCourier(
            @Valid @RequestBody CourierUpdateDto courier,
            @PathVariable("id") int id
    ) {
        return new ResponseEntity<>(courierService.updateCourier(id, courier), OK);
    }

    @GetMapping()
    public ResponseEntity<Map<String, Object>> findByNameSurname(
            @RequestParam(value = "filter", required = false, defaultValue = "all") final String filter,
            @RequestParam(value = "search", required = false, defaultValue = "") final String search,
            @RequestParam(value = "page", required = false, defaultValue = "1") final int page
    ) {

        return new ResponseEntity<>(courierService.getByNameSurname(filter, search, page), OK);
    }


}