package com.ncgroup.marketplaceserver.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import com.ncgroup.marketplaceserver.model.Courier;
import org.springframework.jdbc.core.RowMapper;

import com.ncgroup.marketplaceserver.model.Role;
import com.ncgroup.marketplaceserver.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CourierRowMapper implements RowMapper<Courier> {

    @Override
    public Courier mapRow(ResultSet rs, int rowNum) throws SQLException {
        Courier courier = Courier.builder()
                .user(User.builder()
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
                        .build())
                .status(rs.getBoolean("is_active"))
                .build();

        if (rs.getObject("last_failed_auth", OffsetDateTime.class) != null) {
            courier.getUser().setLastFailedAuth(rs.getObject("last_failed_auth", OffsetDateTime.class)
                    .withOffsetSameInstant(OffsetDateTime.now().getOffset()));
        }
        if (rs.getObject("auth_link_date", OffsetDateTime.class) != null) {
            courier.getUser().setAuthLinkDate(rs.getObject("auth_link_date", OffsetDateTime.class)
                    .withOffsetSameInstant(OffsetDateTime.now().getOffset()));
        }

        log.info("" + courier);
        return courier;
    }

}
