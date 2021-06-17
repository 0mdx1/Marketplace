package com.ncgroup.marketplaceserver.order.repository.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.ncgroup.marketplaceserver.model.dto.UserDisplayInfoDto;
import com.ncgroup.marketplaceserver.model.mapper.UserDisplayInfoRowMapper;
import com.ncgroup.marketplaceserver.order.model.Order;
import com.ncgroup.marketplaceserver.order.model.OrderItem;
import com.ncgroup.marketplaceserver.order.model.OrderStatus;
import com.ncgroup.marketplaceserver.order.model.mapper.DateRowMapper;
import com.ncgroup.marketplaceserver.order.model.mapper.OrderItemRowMapper;
import com.ncgroup.marketplaceserver.order.model.mapper.OrderRowMapper;
import com.ncgroup.marketplaceserver.order.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class OrderRepositoryImpl implements OrderRepository {
	
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Value("${order.insert}")
	private String insertOrderQuery;
	
	@Value("${order.insert-item}")
	private String insertItemQuery;
	
	@Value("${order.select-courier-orders}")
	private String selectCourierOrders;
	
	@Value("${order.select-order}")
	private String selectOrder;
	
	@Value("${order.select-order-goods}")
	private String selectOrderItems;
	
	@Value("${order.update-status}")
	private String updateStatusQuery;
	
	@Value("${order.find-busy-slots}")
	private String selectBusySlots;
	
	@Value("${order.find-couriers-num}")
	private String selectAvalblCouriersNum;;
	
	@Value("${order.find-free-courier-id}")
	private String findFreeCourierIdQuery;
	
	@Value("${user.find-info-for-order}")
	private String findUserInfoForOrderQuery;
	
	@Value("${order.total-pages}")
	private String findTotalPages;
	
	
	@Autowired
	public OrderRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Order> getOrders(long courierId, int page) {
		Object[] params = {courierId, page};
		List<Order> orders = jdbcTemplate.query(selectCourierOrders, new OrderRowMapper(), params);
		for(Order order : orders) {
			List<OrderItem> items = getOrderItems(order.getId());
			order.setItems(items);
		}
		return orders;
	}
	
	
	public Order getOrder(long orderId) {
		List<Order> orders = jdbcTemplate.query(selectOrder, new OrderRowMapper(), new Object[] {orderId});
		Order order = orders.isEmpty() ? null : orders.get(0);
		if(order != null) {
			order.setItems(getOrderItems(order.getId()));
		}
		return order;
	}
	

	@Override
	public Order saveOrderDetails(Order order) {
		KeyHolder orderHolder = new GeneratedKeyHolder();
		SqlParameterSource orderParams = new MapSqlParameterSource()
				.addValue("person_id", order.getUser().getId())
				.addValue("courier_id", order.getCourier().getUser().getId())
				.addValue("delivery_time", order.getDelieveryTime())
				.addValue("address", order.getAddress())
				.addValue("status", order.getStatus().toString())
				.addValue("comment", order.getComment())
				.addValue("disturb", order.isDisturb())
				.addValue("total_sum",  order.getTotalSum())
				.addValue("discount_sum", order.getDiscountSum());
		namedParameterJdbcTemplate.update(insertOrderQuery, orderParams, orderHolder);
		order.setId(orderHolder.getKey().longValue());
		return order;
	}

	@Override
	public void modifyStatus(long id, OrderStatus status) {
		Object[] params = {status.toString(), id};
		log.info(status.toString());
		jdbcTemplate.update(updateStatusQuery, params);
	}

	@Override
	public OrderItem saveOrderGood(OrderItem item, long orderId) {
		SqlParameterSource itemParams = new MapSqlParameterSource()
				.addValue("goods_id", item.getGood().getId())
				.addValue("order_id", orderId)
				.addValue("quantity", item.getQuantity())
				.addValue("sum", item.getPrice());
		namedParameterJdbcTemplate.update(insertItemQuery, itemParams);
		return item;
	}
	
	@Override
	public List<LocalDateTime> findFreeSlots() {
		return jdbcTemplate.query(selectBusySlots, new DateRowMapper());
	}
	
	@Override
	public long getFreeCourierId(LocalDateTime timeSlot) {
		log.info(timeSlot.toString().replace('T', ' ')+":00");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		timeSlot = LocalDateTime.parse(timeSlot.toString().replace('T', ' '), formatter);
		return jdbcTemplate.queryForObject(
				findFreeCourierIdQuery, Long.class, new Object[] {timeSlot.toString().replace('T', ' ')+":00"});
	}
	
	@Override
	public UserDisplayInfoDto findUserForOrder(String email) {
		List<UserDisplayInfoDto> users = jdbcTemplate
				.query(findUserInfoForOrderQuery, new UserDisplayInfoRowMapper(), new Object[] {email});
		return users.isEmpty() ? null : users.get(0);
	}
	
	@Override
	public int getTotalPages(long courierId) {
		return jdbcTemplate.queryForObject(
				findTotalPages, Integer.class, new Object[] {courierId});
	}
	
	@Override
	public int getAvailableCouriersNum() {
		return jdbcTemplate.queryForObject(selectAvalblCouriersNum, Integer.class);
	}
	
	private List<OrderItem> getOrderItems(long orderId) {
		return jdbcTemplate.query(selectOrderItems, new OrderItemRowMapper(), new Object[] {orderId});
	}
	

}
