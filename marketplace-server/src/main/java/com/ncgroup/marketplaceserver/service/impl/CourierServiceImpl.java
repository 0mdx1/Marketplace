package com.ncgroup.marketplaceserver.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.ncgroup.marketplaceserver.model.Courier;
import com.ncgroup.marketplaceserver.repository.CourierRepository;
import com.ncgroup.marketplaceserver.service.CourierService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ncgroup.marketplaceserver.exception.constants.ExceptionMessage;
import com.ncgroup.marketplaceserver.exception.domain.PasswordNotValidException;
import com.ncgroup.marketplaceserver.model.Role;
import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.model.dto.UserDto;
import com.ncgroup.marketplaceserver.repository.UserRepository;
import com.ncgroup.marketplaceserver.security.service.LoginAttemptService;
import com.ncgroup.marketplaceserver.service.EmailSenderService;
import com.ncgroup.marketplaceserver.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Qualifier("userDetailsService")
public class CourierServiceImpl implements CourierService {

    private CourierRepository courierRepository;
    private UserRepository userRepository;
    private UserService userService;
    private BCryptPasswordEncoder passwordEncoder;
    private LoginAttemptService loginAttemptService;
    private EmailSenderService emailSenderService;

    private final int LINK_VALID_TIME_HOUR = 24;

    @Autowired
    public CourierServiceImpl(CourierRepository courierRepository,
                              LoginAttemptService loginAttemptService,
                              EmailSenderService emailSenderService,
                              UserService userService,
                              UserRepository userRepository) {
        this.courierRepository = courierRepository;
        this.loginAttemptService = loginAttemptService;
        this.emailSenderService = emailSenderService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public UserDto create(String name, String surname, String email, String password, String phone) {
        userService.validateNewEmail(StringUtils.EMPTY, email);
        //validate password
        if(!userService.validatePasswordPattern(password)) {
            throw new PasswordNotValidException(ExceptionMessage.PASSWORD_NOT_VALID);
        }
        Courier courier = Courier.builder()
                .user(User.builder()
                        .name(name)
                        .surname(surname)
                        .phone(phone)
                        .email(email)
                        .password(userService.encodePassword(password))
                        .lastFailedAuth(LocalDateTime.now())
                        .role(Role.ROLE_COURIER)
                        .build()
                )
                .status(false)
                .build();

        String authlink = emailSenderService.sendSimpleEmailValidate(email);
        courier.getUser().setAuthLink(authlink);
        User user = userRepository.save(courier.getUser());
        courier.getUser().setId(user.getId());
        //courier = courierRepository.save(courier);
        //log.info("New courier registered");
        return UserDto.convertToDto(courier.getUser());
    }

    @Override
    public Courier getById(int id) {
        return courierRepository.getByid(id);
    }

    @Override
    public List<Courier> getAll() {
        return courierRepository.getAll();
    }


}
