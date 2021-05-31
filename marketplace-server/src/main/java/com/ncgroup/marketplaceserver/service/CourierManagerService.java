package com.ncgroup.marketplaceserver.service;

import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.model.dto.UserDto;

import java.util.List;

public interface CourierManagerService {
    List<User> findAll();
}
