package com.ncgroup.marketplaceserver.controller;

import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.model.dto.UserDto;
import com.ncgroup.marketplaceserver.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/api/manager")
@RestController
public class ManagerController {

    private ManagerService managerService;

    @Autowired
    ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @PostMapping()
    public ResponseEntity<UserDto> manager(@Valid @RequestBody UserDto user) {
        UserDto newUser = managerService.save(
                user.getName(), user.getSurname(), user.getEmail(), user.getPhone(), user.getBirthday(), user.getStatus());
        return new ResponseEntity<>(newUser, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable int id) {
        User manager = managerService.getById(id);
        return new ResponseEntity<>(UserDto.convertToDto(manager), OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateManager(
            @Valid @RequestBody User manager,
            @PathVariable("id") long id
    ) {
        return new ResponseEntity<>(managerService.updateManager(id, manager), OK);
    }

    @GetMapping()
    public ResponseEntity<Map<String, Object>> findByNameSurname(
            @RequestParam(value = "filter", required = false, defaultValue = "all") final String filter,
            @RequestParam(value = "search", required = false, defaultValue = "") final String search,
            @RequestParam(value = "page", required = false, defaultValue = "1") final int page
    ) {

        return new ResponseEntity<>(managerService.getByNameSurname(filter, search, page), OK);
    }

}
