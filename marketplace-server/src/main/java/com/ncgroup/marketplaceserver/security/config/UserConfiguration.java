package com.ncgroup.marketplaceserver.security.config;

import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

public abstract class UserConfiguration {

    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    public UserConfiguration(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    abstract protected User getUser();

    @PostConstruct
    public void init(){
        User user = getUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        createUserIfNotExists(user);
    }

    private void createUserIfNotExists(User user){
        if(userRepository.findByEmail(user.getEmail()) == null){
            userRepository.save(user);
        }
    }
}
