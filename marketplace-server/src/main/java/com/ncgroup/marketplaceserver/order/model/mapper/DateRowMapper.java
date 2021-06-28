package com.ncgroup.marketplaceserver.order.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.springframework.jdbc.core.RowMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateRowMapper implements RowMapper<OffsetDateTime>{

	@Override
	public OffsetDateTime mapRow(ResultSet rs, int rowNum) throws SQLException {
		//return OffsetDateTime.parse(rs.getString("delivery_time"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssx"));
		return rs.getObject("delivery_time", OffsetDateTime.class).withOffsetSameInstant(OffsetDateTime.now().getOffset());
	}
	

}
