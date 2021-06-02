package com.ncgroup.marketplaceserver.service.impl;

import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.repository.UserRepository;
import com.ncgroup.marketplaceserver.service.CourierManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourierManagerServiceImp implements CourierManagerService {
    private UserRepository userRepository;

    @Autowired
    public CourierManagerServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.allCouriersManagers();
    }
}
