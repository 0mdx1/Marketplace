package com.ncgroup.marketplaceserver.repository;

import com.ncgroup.marketplaceserver.model.User;

import java.util.List;

public interface ManagerRepository {
    User getById(long id);
    List<User> getAll();
    User update(User manager, long id);
}
