package com.ncgroup.marketplaceserver.service;

import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.model.dto.UserDto;

import java.time.LocalDate;
import java.util.List;

public interface ManagerService {
    UserDto save(String name, String surname, String email, String phone, LocalDate birthda, String status);

    User getById(long id);

    List<User> getAll();

    User updateManager(long id, User manger);
}
