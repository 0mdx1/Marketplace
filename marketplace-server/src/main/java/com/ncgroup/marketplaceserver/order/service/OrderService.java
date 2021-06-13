package com.ncgroup.marketplaceserver.order.service;

import java.util.List;

import com.ncgroup.marketplaceserver.order.model.OrderStatus;
import com.ncgroup.marketplaceserver.order.model.dto.OrderPostDto;
import com.ncgroup.marketplaceserver.order.model.dto.OrderReadDto;
import com.ncgroup.marketplaceserver.shopping.cart.exceptions.NotFoundException;

public interface OrderService {
	List<OrderReadDto> getCourierOrders(String token, int page);
	OrderReadDto getOrder(long id);
	OrderPostDto addOrder(OrderPostDto order) throws NotFoundException;
	OrderReadDto modifyStatus(long id);
}
