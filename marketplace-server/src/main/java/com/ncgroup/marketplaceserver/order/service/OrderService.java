package com.ncgroup.marketplaceserver.order.service;


import java.time.OffsetDateTime;
import java.util.List;

import com.ncgroup.marketplaceserver.model.dto.UserDisplayInfoDto;
import com.ncgroup.marketplaceserver.order.model.OrderStatus;
import com.ncgroup.marketplaceserver.order.model.dto.OrderPageDto;
import com.ncgroup.marketplaceserver.order.model.dto.OrderPostDto;
import com.ncgroup.marketplaceserver.order.model.dto.OrderReadDto;

public interface OrderService {
    OrderPageDto getCourierOrders(String token, int page);

    List<OffsetDateTime> getFreeSlots();

    OrderReadDto getOrder(long id);

    OrderReadDto addOrder(OrderPostDto order, String token);

    OrderStatus modifyStatus(long id);

    UserDisplayInfoDto getUserInfoForOrder(String token);

    List<OrderReadDto> getUserOrders(String token);

    List<OrderReadDto> getUserHistory(String token);

    UserDisplayInfoDto getCourierInfoForOrder(long id);
}
