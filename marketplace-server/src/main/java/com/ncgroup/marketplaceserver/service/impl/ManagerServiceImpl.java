package com.ncgroup.marketplaceserver.service.impl;

import com.ncgroup.marketplaceserver.model.Role;
import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.model.dto.UserDto;
import com.ncgroup.marketplaceserver.repository.ManagerRepository;
import com.ncgroup.marketplaceserver.repository.UserRepository;
import com.ncgroup.marketplaceserver.service.EmailSenderService;
import com.ncgroup.marketplaceserver.service.ManagerService;
import com.ncgroup.marketplaceserver.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

import java.time.LocalDate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ManagerServiceImpl implements ManagerService {
    private ManagerRepository managerRepository;
    private EmailSenderService emailSenderService;
    private UserService userService;
    private UserRepository userRepository;


    @Autowired
    ManagerServiceImpl(ManagerRepository managerRepository, EmailSenderService emailSenderService,
                       UserService userService, UserRepository userRepository) {
        this.managerRepository = managerRepository;
        this.emailSenderService = emailSenderService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public UserDto save(String name, String surname, String email, String phone, LocalDate birthday) {
        userService.validateNewEmail(StringUtils.EMPTY, email);
        User user = User.builder()
                .name(name)
                .surname(surname)
                .phone(phone)
                .email(email)
                .birthday(birthday)
                .lastFailedAuth(LocalDateTime.now())
                .role(Role.ROLE_PRODUCT_MANAGER)
                .build();
        String authlink = emailSenderService.sendSimpleEmailPasswordCreation(email);
        user.setAuthLink(authlink);
        user = userRepository.save(user);
        log.info("New manager registered");
        return UserDto.convertToDto(user);
    }

    @Override
    public User getById(int id) {
        return managerRepository.getById(id);
    }

    @Override
    public List<User> getAll() {
        return managerRepository.getAll();
    }
}
