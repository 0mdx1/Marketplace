package com.ncgroup.marketplaceserver.order.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

public class DateRowMapper implements RowMapper<LocalDateTime>{

	@Override
	public LocalDateTime mapRow(ResultSet rs, int rowNum) throws SQLException {
		return rs.getObject("delivery_time", LocalDateTime.class);
	}
	

}
