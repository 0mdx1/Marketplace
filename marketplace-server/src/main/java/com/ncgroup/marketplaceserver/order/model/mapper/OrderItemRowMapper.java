package com.ncgroup.marketplaceserver.order.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ncgroup.marketplaceserver.goods.model.Good;
import com.ncgroup.marketplaceserver.goods.model.Unit;
import com.ncgroup.marketplaceserver.order.model.OrderItem;


public class OrderItemRowMapper implements RowMapper<OrderItem> {

	@Override
	public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
		return OrderItem.builder()
				.good(
						Good.builder()
						.id(rs.getLong("id"))
						.goodName(rs.getString("prod_name"))
						.categoryName(rs.getString("cat_name"))
						.firmName(rs.getString("firm_name"))
						.unit(Unit.valueOf(rs.getString("unit")))
						.build()
						)
				.quantity(rs.getInt("quantity"))
				.price(rs.getFloat("sum"))
				.build();
	}

}
