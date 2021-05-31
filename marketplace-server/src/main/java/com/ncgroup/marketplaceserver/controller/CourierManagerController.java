package com.ncgroup.marketplaceserver.controller;

import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.model.dto.UserDto;
import com.ncgroup.marketplaceserver.service.CourierManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.OK;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(
        path = "api/courier-manager",
        produces = "application/json"
)
public class CourierManagerController {
    private CourierManagerService courierManagerService;

    @Autowired
    CourierManagerController(CourierManagerService courierManagerService) {
        this.courierManagerService = courierManagerService;
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return new ResponseEntity<>(courierManagerService.findAll(), OK);
    }
}
