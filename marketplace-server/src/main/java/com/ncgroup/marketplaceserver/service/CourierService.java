package com.ncgroup.marketplaceserver.service;

import java.util.List;

import com.ncgroup.marketplaceserver.model.Courier;
import com.ncgroup.marketplaceserver.model.Role;
import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.model.dto.UserDto;

public interface CourierService {
    UserDto create(String name, String surname, String email, String password, String phone);
    Courier getById(int id);
    List<Courier> getAll();
}
