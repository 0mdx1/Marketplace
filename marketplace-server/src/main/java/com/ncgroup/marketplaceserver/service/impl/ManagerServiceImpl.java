package com.ncgroup.marketplaceserver.service.impl;

import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.repository.ManagerRepository;
import com.ncgroup.marketplaceserver.repository.UserRepository;
import com.ncgroup.marketplaceserver.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {
    private ManagerRepository managerRepository;


    @Autowired
    ManagerServiceImpl(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    public User getById(int id) {
        return managerRepository.getById(id);
    }

    @Override
    public List<User> getAll() {
        return managerRepository.getAll();
    }
}
