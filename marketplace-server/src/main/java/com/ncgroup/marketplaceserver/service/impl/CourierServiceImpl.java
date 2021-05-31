package com.ncgroup.marketplaceserver.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.ncgroup.marketplaceserver.exception.constants.ExceptionMessage;
import com.ncgroup.marketplaceserver.exception.domain.EmailExistException;
import com.ncgroup.marketplaceserver.exception.domain.InvalidStatusException;
import com.ncgroup.marketplaceserver.model.Courier;
import com.ncgroup.marketplaceserver.model.dto.CourierDto;
import com.ncgroup.marketplaceserver.model.dto.CourierUpdateDto;
import com.ncgroup.marketplaceserver.repository.CourierRepository;
import com.ncgroup.marketplaceserver.service.CourierService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.ncgroup.marketplaceserver.model.Role;
import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.model.dto.UserDto;
import com.ncgroup.marketplaceserver.repository.UserRepository;
import com.ncgroup.marketplaceserver.security.service.LoginAttemptService;
import com.ncgroup.marketplaceserver.service.EmailSenderService;
import com.ncgroup.marketplaceserver.service.UserService;
import com.ncgroup.marketplaceserver.constants.StatusConstants;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public UserDto save(String name, String surname, String email, String phone,
                        LocalDate birthday, String status) {
        userService.validateNewEmail(StringUtils.EMPTY, email);
        boolean isEnabled;
        boolean isActive;
        if(status.equals(StatusConstants.TERMINATED)){
            isEnabled = false;
            isActive = false;
        }
        else{
            isEnabled = true;
            if(status.equals(StatusConstants.ACTIVE)){
                isActive = true;
            }
            else if (status.equals(StatusConstants.INACTIVE)){
                isActive = false;
            }
            else throw new InvalidStatusException(ExceptionMessage.INVALID_COURIER_STATUS);
        }
        Courier courier = Courier.builder()
                .user(User.builder()
                        .name(name)
                        .surname(surname)
                        .phone(phone)
                        .email(email)
                        .birthday(birthday)
                        .lastFailedAuth(LocalDateTime.now())
                        .role(Role.ROLE_COURIER)
                        .isEnabled(isEnabled)
                        .build()
                )
                .status(isActive)
                .build();
        String authlink = emailSenderService.sendSimpleEmailPasswordCreation(email);
        courier.getUser().setAuthLink(authlink);
        User user = userRepository.save(courier.getUser());
        courier.getUser().setId(user.getId());
        System.out.println(courier);
        courier = courierRepository.save(courier);
        log.info("New courier registered");
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

    @Override
    public Courier updateCourier(int id, CourierUpdateDto courier) {
        Courier currentCourier = this.getById(id);
        courier.toDto(currentCourier);
        return courierRepository.update(currentCourier, id);
    }


}
