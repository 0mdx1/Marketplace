package com.ncgroup.marketplaceserver.controller;

import static org.springframework.http.HttpStatus.OK;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ncgroup.marketplaceserver.model.dto.CourierDto;
import com.ncgroup.marketplaceserver.model.dto.CourierUpdateDto;
import com.ncgroup.marketplaceserver.model.dto.UserDto;
import com.ncgroup.marketplaceserver.service.CourierService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api/courier")
@RestController
@Slf4j
public class CourierController {
    private CourierService courierService;

    @Autowired
    public CourierController(CourierService courierService) {
        this.courierService = courierService;
    }

    @PostMapping()
    public ResponseEntity<UserDto> create(@Valid @RequestBody CourierDto courier) {
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