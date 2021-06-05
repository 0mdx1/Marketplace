package com.ncgroup.marketplaceserver.repository;

import java.util.List;

import com.ncgroup.marketplaceserver.constants.StatusConstants;
import com.ncgroup.marketplaceserver.model.Courier;
import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.model.dto.CourierDto;
import com.ncgroup.marketplaceserver.model.dto.CourierUpdateDto;
import com.ncgroup.marketplaceserver.model.dto.UserDto;

public interface CourierRepository {
    Courier save(Courier courier);

    Courier getByid(long id);

    List<Courier> getAll();

    CourierUpdateDto update(CourierUpdateDto courier, long id, boolean isEnabled, boolean isActive);

    List<Courier> getByNameSurname(String search);


}
