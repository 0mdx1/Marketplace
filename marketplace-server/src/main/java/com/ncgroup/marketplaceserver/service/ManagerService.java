package com.ncgroup.marketplaceserver.service;

import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.model.dto.UserDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ManagerService {
    UserDto save(String name, String surname, String email, String phone, LocalDate birthda, String status);

    User getById(long id);

    User updateManager(long id, User manger);

    Map<String, Object> getByNameSurname(String filter, String search, int page);
}
