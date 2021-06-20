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
public class DefaultManagerConfiguration extends UserConfiguration {
	@Value("${default-manager.name}")
    private String name;

    @Value("${default-manager.surname}")
    private String surname;

    @Value("${default-manager.phone}")
    private String phone;

    @Value("${default-manager.email}")
    private String email;

    @Value("${default-manager.password}")
    private String password;

    @Autowired
    public DefaultManagerConfiguration(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository, 
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
                .role(Role.ROLE_PRODUCT_MANAGER)
                .isEnabled(true)
                .build();
    }
}
