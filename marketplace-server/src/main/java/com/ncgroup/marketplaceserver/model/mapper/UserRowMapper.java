package com.ncgroup.marketplaceserver.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.springframework.jdbc.core.RowMapper;

import com.ncgroup.marketplaceserver.model.Role;
import com.ncgroup.marketplaceserver.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserRowMapper implements RowMapper<User>{

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = User.builder()
				.id(rs.getLong("id"))
				.name(rs.getString("name"))
				.surname(rs.getString("surname"))
				.phone(rs.getString("phone"))
				.email(rs.getString("email"))
				.birthday(rs.getObject("birthday", LocalDate.class))
				.isEnabled(rs.getBoolean("is_enabled"))
				.failedAuth(rs.getInt("failed_auth"))
				.password(rs.getString("password"))
				.role(Role.valueOf(rs.getString("role")))
				.authLink(rs.getString("auth_link"))
				.build();
		if(rs.getBoolean("is_enabled")) {
			user.setStatus("active");
		}else {
			user.setStatus("terminated");
		}
		if(rs.getObject("last_failed_auth", OffsetDateTime.class) != null) {
			user.setLastFailedAuth(rs.getObject("last_failed_auth", OffsetDateTime.class)
						.withOffsetSameInstant(OffsetDateTime.now().getOffset()));
		}
		log.info("AUTH LINK "  + rs.getObject("auth_link_date", OffsetDateTime.class));
		if(rs.getObject("auth_link_date", OffsetDateTime.class) != null) {
			user.setAuthLinkDate(rs.getObject("auth_link_date", OffsetDateTime.class)
						.withOffsetSameInstant(OffsetDateTime.now().getOffset()));
		}
						

		log.info(""+user.isEnabled());
		return user;
	}

}
