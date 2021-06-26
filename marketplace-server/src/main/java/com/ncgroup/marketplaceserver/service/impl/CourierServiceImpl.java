package com.ncgroup.marketplaceserver.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ncgroup.marketplaceserver.exception.constants.ExceptionMessage;
import com.ncgroup.marketplaceserver.exception.domain.InvalidStatusException;
import com.ncgroup.marketplaceserver.model.Courier;
import com.ncgroup.marketplaceserver.model.dto.CourierUpdateDto;
import com.ncgroup.marketplaceserver.repository.CourierRepository;
import com.ncgroup.marketplaceserver.service.CourierService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import com.ncgroup.marketplaceserver.model.Role;
import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.model.dto.UserDto;
import com.ncgroup.marketplaceserver.repository.UserRepository;
import com.ncgroup.marketplaceserver.service.EmailSenderService;
import com.ncgroup.marketplaceserver.service.UserService;
import com.ncgroup.marketplaceserver.constants.StatusConstants;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;

@Slf4j
@Service
@Qualifier("userDetailsService")
@PropertySource("classpath:application.properties")
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final EmailSenderService emailSenderService;


    @Value("${page.capacity}")
    private Integer PAGE_SIZE;

    @Autowired
    public CourierServiceImpl(CourierRepository courierRepository,
                              EmailSenderService emailSenderService,
                              UserService userService,
                              UserRepository userRepository) {
        this.courierRepository = courierRepository;
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
        if (status.equals(StatusConstants.TERMINATED)) {
            isEnabled = false;
            isActive = false;
        } else {
            isEnabled = true;
            if (status.equals(StatusConstants.ACTIVE)) {
                isActive = true;
            } else if (status.equals(StatusConstants.INACTIVE)) {
                isActive = false;
            } else throw new InvalidStatusException(ExceptionMessage.INVALID_COURIER_STATUS);
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

        String authlink = null;
        try {
            authlink = emailSenderService.sendSimpleEmailPasswordCreation(email, name);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
        courier.getUser().setAuthLink(authlink);
        User user = userRepository.save(courier.getUser());
        courier.getUser().setId(user.getId());
        courier = courierRepository.save(courier);
        log.info("New courier registered");
        return UserDto.convertToDto(courier.getUser());
    }

    @Override
    public User getById(long id) {
        Courier courier = courierRepository.getByid(id);
        User courierUser = courier.getUser();
        courierUser.setStatus(calculateStatus(courierUser.isEnabled(), courier.isStatus()));
        return courierUser;
    }

    private String calculateStatus(boolean isEnabled, boolean isStatus) {
        if (isEnabled) {
            return isStatus ? StatusConstants.ACTIVE : StatusConstants.INACTIVE;
        }
        return StatusConstants.TERMINATED;
    }

    @Override
    public CourierUpdateDto updateCourier(long id, CourierUpdateDto courier) {
        boolean isActive;
        boolean isEnabled;
        User currentCourier = this.getById(id);
        if (courier.getStatus().equals(StatusConstants.ACTIVE)) {
            isEnabled = true;
            isActive = true;
        } else if (courier.getStatus().equals(StatusConstants.INACTIVE)) {
            isEnabled = true;
            isActive = false;
        } else {
            isActive = false;
            isEnabled = false;
        }
        courier.toDto(currentCourier);

        return courierRepository.update(courier, id, isEnabled, isActive);
    }

    private List<User> calculateStatusForCollection(List<Courier> couriers) {
        List<User> userList = new LinkedList<>();
        for (Courier courier : couriers) {
            User userTemp = courier.getUser();
            userTemp.setStatus(calculateStatus(userTemp.isEnabled(), courier.isStatus()));
            userList.add(userTemp);
        }
        return userList;
    }

    @Override
    public Map<String, Object> getByNameSurname(String filter, String search, int page) {
        List<Courier> courierList = null;
        int allPages = 0;

        if (StatusConstants.ACTIVE.equals(filter)) {
            courierList = courierRepository
                    .getByNameSurname(search, true, true, (page - 1) * PAGE_SIZE);
            allPages = courierRepository.getNumberOfRows(search, true, true);
        } else if (StatusConstants.INACTIVE.equals(filter)) {
            courierList = courierRepository
                    .getByNameSurname(search, true, false, (page - 1) * PAGE_SIZE);
            allPages = courierRepository.getNumberOfRows(search, true, false);
        } else if (StatusConstants.TERMINATED.equals(filter)) {
            courierList = courierRepository
                    .getByNameSurname(search, false, false, (page - 1) * PAGE_SIZE);
            allPages = courierRepository.getNumberOfRows(search, false, false);
        }else {
            courierList = courierRepository.getByNameSurnameAll(search, (page - 1) * PAGE_SIZE);
            allPages = courierRepository.getNumberOfRowsAll(search);
        }
        List<User> couriers = calculateStatusForCollection(courierList);

        Map<String, Object> result = new HashMap<>();
        result.put("users", couriers);
        result.put("currentPage", page);
        result.put("pageNum",
                allPages % PAGE_SIZE == 0 ? allPages / PAGE_SIZE : allPages / PAGE_SIZE + 1);
        return result;
    }


}
