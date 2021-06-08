package com.ncgroup.marketplaceserver.repository;

import com.ncgroup.marketplaceserver.model.User;

import java.util.List;

public interface ManagerRepository {
    User getById(long id);

    User update(User manager, long id);

    List<User> getByNameSurname(String search, boolean status, int page);

    int getNumberOfRows(String search, boolean status);

    int getNumberOfRowsAll(String search);

    List<User> getByNameSurnameAll(String search, int page);
}
