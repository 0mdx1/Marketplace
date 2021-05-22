package com.ncgroup.marketplaceserver.security.config;

import com.ncgroup.marketplaceserver.model.Role;
import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.repository.UserRepository;
import com.ncgroup.marketplaceserver.security.util.EncoderPrivider;
import com.ncgroup.marketplaceserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DefaultUserConfiguration {

    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    @Autowired
    public DefaultUserConfiguration(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init(){
        User defaultAdmin = getDefaultAdmin();
        createUserIfNotExists(defaultAdmin);
        User defaultUser = getDefaultUser();
        createUserIfNotExists(defaultUser);
    }

    private void createUserIfNotExists(User user){
        if(userRepository.findByEmail(user.getEmail()) == null){
            userRepository.save(user);
        }
    }

    private User getDefaultAdmin() {
        return User
                .builder()
                .name("AdminName")
                .surname("AdminSurname")
                .email("admin@admin.com")
                .password(passwordEncoder.encode("nimda"))
                .role(Role.ROLE_ADMIN)
                .isEnabled(true)
                .build();
    }

    private User getDefaultUser() {
        return User
                .builder()
                .name("UserName")
                .surname("UserSurname")
                .email("user@user.com")
                .password(passwordEncoder.encode("resu"))
                .role(Role.ROLE_USER)
                .isEnabled(true)
                .build();
    }
}
