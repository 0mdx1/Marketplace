package com.ncgroup.marketplaceserver.security.config;

import com.ncgroup.marketplaceserver.model.Role;
import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Configuration
public class DefaultAdminConfiguration extends UserConfiguration{

    @Value("${default-admin.name}")
    private String name;

    @Value("${default-admin.surname}")
    private String surname;

    @Value("${default-admin.email}")
    private String email;

    @Value("${default-admin.password}")
    private String password;

    @Autowired
    public DefaultAdminConfiguration(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository) {
        super(passwordEncoder, userRepository);
    }

    protected User getUser() {
        return User
                .builder()
                .name(name)
                .surname(surname)
                .email(email)
                .password(password)
                .role(Role.ROLE_ADMIN)
                .isEnabled(true)
                .build();
    }
}
