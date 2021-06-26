package com.ncgroup.marketplaceserver.order.repository;

import java.time.OffsetDateTime;
import java.util.List;

import com.ncgroup.marketplaceserver.model.dto.UserDisplayInfoDto;
import com.ncgroup.marketplaceserver.order.model.Order;
import com.ncgroup.marketplaceserver.order.model.OrderItem;
import com.ncgroup.marketplaceserver.order.model.OrderStatus;

public interface OrderRepository {
	
	List<Order> getOrders(long courierId, int page);
	Order getOrder(long orderId);
	Order saveOrderDetails(Order order);
	OrderItem saveOrderGood(OrderItem item, long orderId);
	void modifyStatus(long id, OrderStatus status);
	List<OffsetDateTime> findBusySlots();
	long getFreeCourierId(OffsetDateTime timeSlot);
	UserDisplayInfoDto findUserForOrder(String email);
	UserDisplayInfoDto findCourierForOrder(long orderId);
	int getTotalPages(long courierId);
	int getAvailableCouriersNum();
	List<Order> getUserIncomingOrders(String email);
	List<Order> getUserHistoryOrders(String email);
}
