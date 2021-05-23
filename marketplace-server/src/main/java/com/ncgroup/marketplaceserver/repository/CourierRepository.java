package com.ncgroup.marketplaceserver.repository;

import java.util.List;

import com.ncgroup.marketplaceserver.model.Courier;
import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.model.dto.UserDto;

public interface CourierRepository {
    Courier save(Courier user);
    Courier getByid(int id);
    List<Courier> getAll();
}
