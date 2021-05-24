package com.ncgroup.marketplaceserver.controller;

import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/api/manager")
@RestController
public class ManagerController {

    private ManagerService managerService;

    @Autowired
    ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable int id) {
        User manager = managerService.getById(id);
        return new ResponseEntity<>(manager, OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> findAll() {
        List<User> managers = managerService.getAll();
        return new ResponseEntity<>(managers, OK);
    }

}
