package com.ncgroup.marketplaceserver.security.config;

import javax.annotation.PostConstruct;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ncgroup.marketplaceserver.model.Courier;
import com.ncgroup.marketplaceserver.model.Role;
import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.repository.CourierRepository;
import com.ncgroup.marketplaceserver.repository.UserRepository;

public abstract class UserConfiguration {

    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    private CourierRepository courierRepository;

    public UserConfiguration(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository,
                             CourierRepository courierRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.courierRepository = courierRepository;
    }

    abstract protected User getUser();

    @PostConstruct
    public void init() {
        User user = getUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        createUserIfNotExists(user);
    }

    private void createUserIfNotExists(User user) {
        if (userRepository.findByEmail(user.getEmail()) == null) {
            if (user.getRole() == Role.ROLE_COURIER) {
                Courier courier = Courier.builder()
                        .user(user)
                        .status(true)
                        .build();
                user = userRepository.save(courier.getUser());
                courier.getUser().setId(user.getId());
                courierRepository.save(courier);
            } else {
                userRepository.save(user);
            }

        }
    }
}
