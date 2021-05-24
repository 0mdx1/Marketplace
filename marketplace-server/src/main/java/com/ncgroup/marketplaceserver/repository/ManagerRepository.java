package com.ncgroup.marketplaceserver.repository;

import com.ncgroup.marketplaceserver.model.User;

import java.util.List;

public interface ManagerRepository {
    User getById(int id);
    List<User> getAll();
}
