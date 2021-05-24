package com.ncgroup.marketplaceserver.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import com.ncgroup.marketplaceserver.model.Courier;
import org.springframework.jdbc.core.RowMapper;

import com.ncgroup.marketplaceserver.model.Role;
import com.ncgroup.marketplaceserver.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CourierRowMapper implements RowMapper<Courier>{

    @Override
    public Courier mapRow(ResultSet rs, int rowNum) throws SQLException {
        Courier courier = Courier.builder()
                .user(User.builder()
                    .name(rs.getString("name"))
                    .surname(rs.getString("surname"))
                    .phone(rs.getString("phone"))
                    .email(rs.getString("email"))
                    .isEnabled(rs.getBoolean("is_enabled"))
                    .failedAuth(rs.getInt("failed_auth"))
                    .lastFailedAuth(rs.getObject("last_failed_auth", LocalDateTime.class))
                    .password(rs.getString("password"))
                    .role(Role.valueOf(rs.getString("role")))
                    .authLink(rs.getString("auth_link"))
                    .authLinkDate(rs.getObject("auth_link_date", LocalDateTime.class))
                    .build())
                .status(rs.getBoolean("is_active"))
                .build();

        log.info(""+courier);
        return courier;
    }

}
