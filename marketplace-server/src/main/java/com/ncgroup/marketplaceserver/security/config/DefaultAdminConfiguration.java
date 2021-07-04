package com.ncgroup.marketplaceserver.security.config;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ncgroup.marketplaceserver.model.Role;
import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.repository.CourierRepository;
import com.ncgroup.marketplaceserver.repository.UserRepository;

@Configuration
public class DefaultAdminConfiguration extends UserConfiguration {

    @Value("${default-admin.name}")
    private String name;

    @Value("${default-admin.surname}")
    private String surname;

    @Value("${default-admin.phone}")
    private String phone;

    @Value("${default-admin.email}")
    private String email;

    @Value("${default-admin.password}")
    private String password;

    @Autowired
    public DefaultAdminConfiguration(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository,
                                     CourierRepository courierRepository) {
        super(passwordEncoder, userRepository, courierRepository);
    }

    protected User getUser() {
        return User
                .builder()
                .name(name)
                .surname(surname)
                .phone(phone)
                .birthday(LocalDate.now())
                .email(email)
                .password(password)
                .role(Role.ROLE_ADMIN)
                .isEnabled(true)
                .build();
    }
}
