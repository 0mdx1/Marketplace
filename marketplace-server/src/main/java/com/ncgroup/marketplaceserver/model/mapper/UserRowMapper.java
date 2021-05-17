package com.ncgroup.marketplaceserver.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import com.ncgroup.marketplaceserver.model.Role;
import com.ncgroup.marketplaceserver.model.User;

public class UserRowMapper implements RowMapper<User>{

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = User.builder()
				.id(rs.getLong("id"))
				.name(rs.getString("name"))
				.surname(rs.getString("surname"))
				.phone(rs.getString("phone"))
				.email(rs.getString("email"))
				.isEnabled(rs.getBoolean("is_enabled"))
				.failedAuth(rs.getInt("failed_auth"))
				.lastFailedAuth(rs.getObject("last_failed_auth", LocalDateTime.class))
				.password(rs.getString("password"))
				.role(Role.valueOf(rs.getString("role")))
				.build();
		return user;
	}

}
