package com.ncgroup.marketplaceserver.service;

import java.time.LocalDate;
import java.util.List;

import com.ncgroup.marketplaceserver.model.Courier;
import com.ncgroup.marketplaceserver.model.dto.CourierDto;

public interface CourierService {
    CourierDto save(String name, String surname, String email, String phone, LocalDate birthday, boolean status);
    Courier getById(int id);
    List<Courier> getAll();
}
