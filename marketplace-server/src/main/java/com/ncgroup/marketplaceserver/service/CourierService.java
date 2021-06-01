package com.ncgroup.marketplaceserver.service;

import java.time.LocalDate;
import java.util.List;

import com.ncgroup.marketplaceserver.model.Courier;
import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.model.dto.CourierDto;
import com.ncgroup.marketplaceserver.model.dto.CourierUpdateDto;
import com.ncgroup.marketplaceserver.model.dto.UserDto;

public interface CourierService {
    UserDto save(String name, String surname, String email, String phone, LocalDate birthday, String status);

    User getById(long id);

    List<User> getAll();

    CourierUpdateDto updateCourier(long id, CourierUpdateDto courier);
}
