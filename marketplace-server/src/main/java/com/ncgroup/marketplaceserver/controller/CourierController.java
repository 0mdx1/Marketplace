package com.ncgroup.marketplaceserver.controller;

import static org.springframework.http.HttpStatus.OK;
import java.util.List;
import javax.validation.Valid;

import com.ncgroup.marketplaceserver.model.Courier;
import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.model.dto.CourierDto;
import com.ncgroup.marketplaceserver.model.dto.CourierUpdateDto;
import com.ncgroup.marketplaceserver.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import com.ncgroup.marketplaceserver.model.dto.UserDto;
import com.ncgroup.marketplaceserver.security.util.JwtProvider;

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
    public ResponseEntity<UserDto> create (@Valid @RequestBody CourierDto courier){
        UserDto newCourier = courierService.save(
                courier.getName(), courier.getSurname(), courier.getEmail(),
                courier.getPhone(), courier.getBirthday(), courier.getStatus());
        return new ResponseEntity<>(newCourier, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable int id) {
        return new ResponseEntity<>(courierService.getById(id), OK);
    }

    @GetMapping()
    public ResponseEntity<List<User>> findAll() {
        return new ResponseEntity<>(courierService.getAll(), OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CourierUpdateDto> updateCourier(
            @Valid @RequestBody CourierUpdateDto courier,
            @PathVariable("id") int id
    ) {
        return new ResponseEntity<>(courierService.updateCourier(id, courier), OK);
    }

    @GetMapping(params = {"filter", "search", "page"})
    public String findAllByFilter(
            @RequestParam("filter") final String filter,
            @RequestParam("search") final String search,
            @RequestParam("page") final int page
    ) {
        return filter + search + page;
    }


}