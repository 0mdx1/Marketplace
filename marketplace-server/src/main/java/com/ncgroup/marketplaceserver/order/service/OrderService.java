package com.ncgroup.marketplaceserver.order.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.ncgroup.marketplaceserver.model.dto.UserDisplayInfoDto;
import com.ncgroup.marketplaceserver.order.model.OrderStatus;
import com.ncgroup.marketplaceserver.order.model.dto.OrderPostDto;
import com.ncgroup.marketplaceserver.order.model.dto.OrderReadDto;

public interface OrderService {
	Map<String, Object> getCourierOrders(String token, int page);
	List<LocalDateTime> getFreeSlots();
	OrderReadDto getOrder(long id);
	OrderReadDto addOrder(OrderPostDto order, String token);
	OrderStatus modifyStatus(long id);
	UserDisplayInfoDto getUserInfoForOrder(String token);
}
