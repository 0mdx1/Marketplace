package com.ncgroup.marketplaceserver.service;

import com.ncgroup.marketplaceserver.model.User;

import java.util.List;

public interface ManagerService {
    User getById(int id);
    List<User> getAll();
}
