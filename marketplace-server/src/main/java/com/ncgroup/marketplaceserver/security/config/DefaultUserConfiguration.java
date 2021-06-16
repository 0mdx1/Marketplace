package com.ncgroup.marketplaceserver.security.config;

import com.ncgroup.marketplaceserver.model.Role;
import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Configuration
public class DefaultUserConfiguration extends UserConfiguration{

    @Value("${default-user.name}")
    private String name;

    @Value("${default-user.surname}")
    private String surname;

    @Value("${default-user.phone}")
    private String phone;

    @Value("${default-user.email}")
    private String email;

    @Value("${default-user.password}")
    private String password;

    @Autowired
    public DefaultUserConfiguration(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository) {
        super(passwordEncoder, userRepository);
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
                .role(Role.ROLE_USER)
                .isEnabled(true)
                .build();
    }
}
