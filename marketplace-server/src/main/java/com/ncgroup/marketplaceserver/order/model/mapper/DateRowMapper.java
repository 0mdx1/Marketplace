package com.ncgroup.marketplaceserver.order.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import org.springframework.jdbc.core.RowMapper;

public class DateRowMapper implements RowMapper<OffsetDateTime> {

    @Override
    public OffsetDateTime mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getObject("delivery_time", OffsetDateTime.class).withOffsetSameInstant(OffsetDateTime.now().getOffset());
    }


}
