package com.ncgroup.marketplaceserver.model.mapper;

import com.ncgroup.marketplaceserver.model.Role;
import com.ncgroup.marketplaceserver.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


@Slf4j
public class CourierManagerRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        User user = User.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .surname(rs.getString("surname"))
                .phone(rs.getString("phone"))
                .email(rs.getString("email"))
                .birthday(rs.getObject("birthday", LocalDate.class))
                .isEnabled(rs.getBoolean("is_enabled"))
                .role(Role.valueOf(rs.getString("role")))
                .build();
        log.info(""+user.isEnabled());
        return user;
    }
}
