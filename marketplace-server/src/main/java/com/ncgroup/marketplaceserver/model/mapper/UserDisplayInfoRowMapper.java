package com.ncgroup.marketplaceserver.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ncgroup.marketplaceserver.model.dto.UserDisplayInfoDto;

public class UserDisplayInfoRowMapper implements RowMapper<UserDisplayInfoDto> {

	@Override
	public UserDisplayInfoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return UserDisplayInfoDto
				.builder()
				.id(rs.getLong("id"))
				.name(rs.getString("name"))
				.surname(rs.getString("surname"))
				.phone(rs.getString("phone"))
				.build();
	}

}
