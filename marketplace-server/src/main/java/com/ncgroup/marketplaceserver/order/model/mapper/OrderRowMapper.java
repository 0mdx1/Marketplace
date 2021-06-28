package com.ncgroup.marketplaceserver.order.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import org.springframework.jdbc.core.RowMapper;

import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.order.model.Order;
import com.ncgroup.marketplaceserver.order.model.OrderStatus;

public class OrderRowMapper implements RowMapper<Order> {

	@Override
	public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
		Order order = Order.builder()
				.id(rs.getInt("id"))
				.user(
						User.builder()
						.id(rs.getInt("person_id"))
						.name(rs.getString("name"))
						.surname(rs.getString("surname"))
						.phone(rs.getString("phone"))
						.build()
						)
				.deliveryTime(rs.getObject("delivery_time", OffsetDateTime.class)
						.withOffsetSameInstant(OffsetDateTime.now().getOffset()))
				.address(rs.getString("address"))
				.status(OrderStatus.valueOf(rs.getString("status")))
				.comment(rs.getString("comment"))
				.disturb(rs.getBoolean("disturb"))
				.totalSum(rs.getFloat("total_sum"))
				.build();
		return order;
	}

}
