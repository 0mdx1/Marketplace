package com.ncgroup.marketplaceserver.service.impl;

import com.ncgroup.marketplaceserver.constants.StatusConstants;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import com.ncgroup.marketplaceserver.exception.constants.ExceptionMessage;
import com.ncgroup.marketplaceserver.exception.domain.InvalidStatusException;

import java.time.LocalDate;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import javax.mail.MessagingException;

@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class ManagerServiceImpl implements ManagerService {
    private ManagerRepository managerRepository;
    private EmailSenderService emailSenderService;
    private UserService userService;
    private UserRepository userRepository;

    @Value("${page.capacity}")
    private Integer PAGE_SIZE;

    @Autowired
    ManagerServiceImpl(ManagerRepository managerRepository, EmailSenderService emailSenderService,
                       UserService userService, UserRepository userRepository) {
        this.managerRepository = managerRepository;
        this.emailSenderService = emailSenderService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public UserDto save(String name, String surname, String email, String phone, LocalDate birthday, String status) {
        userService.validateNewEmail(StringUtils.EMPTY, email);
        boolean isEnabled;
        if (status.equals(StatusConstants.TERMINATED)) {
            isEnabled = false;
        } else if (status.equals(StatusConstants.ACTIVE)) {
            isEnabled = true;
        } else {
            throw new InvalidStatusException(ExceptionMessage.INVALID_MANAGER_STATUS);
        }
        User user = User.builder()
                .name(name)
                .surname(surname)
                .phone(phone)
                .email(email)
                .birthday(birthday)
                .isEnabled(true)
                .lastFailedAuth(LocalDateTime.now())
                .role(Role.ROLE_PRODUCT_MANAGER)
                .isEnabled(isEnabled)
                .build();
        String authlink = null;
        try {
            authlink = emailSenderService.sendSimpleEmailPasswordCreation(email, name);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
        user.setAuthLink(authlink);
        user = userRepository.save(user);
        log.info("New manager registered");
        return UserDto.convertToDto(user);
    }

    @Override
    public User getById(long id) {
        User manager = managerRepository.getById(id);
        if (manager.isEnabled()) {
            manager.setStatus(StatusConstants.ACTIVE);
        } else {
            manager.setStatus(StatusConstants.TERMINATED);
        }
        return manager;
    }

    @Override
    public User updateManager(long id, User manager) {
        User currentManager = this.getById(id);
        manager.toDto(currentManager);
        return managerRepository.update(currentManager, id);
    }

    @Override
    public Map<String, Object> getByNameSurname(String filter, String search, int page) {
        List<User> managers = null;
        int allPages = 0;

        if (StatusConstants.ACTIVE.equals(filter)) {
            managers = managerRepository.getByNameSurname(search, true, (page - 1) * PAGE_SIZE);
            allPages = managerRepository.getNumberOfRows(search, true);
        } else if (StatusConstants.TERMINATED.equals(filter)) {
            managers = managerRepository.getByNameSurname(search, false, (page - 1) * PAGE_SIZE);
            allPages = managerRepository.getNumberOfRows(search, false);
        } else {
            managers = managerRepository.getByNameSurnameAll(search, (page - 1) * PAGE_SIZE);
            allPages = managerRepository.getNumberOfRowsAll(search);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("users", managers);
        result.put("currentPage", page);
        result.put("pageNum", allPages % PAGE_SIZE == 0 ? allPages / PAGE_SIZE : allPages / PAGE_SIZE + 1);

        return result;
    }
}
