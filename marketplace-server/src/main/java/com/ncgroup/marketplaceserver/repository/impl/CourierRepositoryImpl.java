package com.ncgroup.marketplaceserver.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.ncgroup.marketplaceserver.constants.StatusConstants;
import com.ncgroup.marketplaceserver.model.Courier;
import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.model.dto.CourierDto;
import com.ncgroup.marketplaceserver.model.dto.CourierUpdateDto;
import com.ncgroup.marketplaceserver.model.mapper.CourierRowMapper;
import com.ncgroup.marketplaceserver.repository.CourierRepository;
import com.ncgroup.marketplaceserver.shopping.cart.model.ShoppingCartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@PropertySource("classpath:database/queries.properties")
public class CourierRepositoryImpl implements CourierRepository {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${courier.insert}")
    private String insertCourierQuery;

    @Value("${courier.find-by-id}")
    private String selectById;

    @Value("${courier.find-all}")
    private String selectAll;


    @Value("${courier.update}")
    private String updateCourier;

    @Value("${courier.find-by-name-surname}")
    private String filterNameQuery;


    @Autowired
    public CourierRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public Courier save(Courier courier) {
        SqlParameterSource userParameters = new MapSqlParameterSource()
                .addValue("person_id", courier.getUser().getId())
                .addValue("is_active", courier.isStatus());
        namedParameterJdbcTemplate.update(insertCourierQuery, userParameters);
        return courier;
    }

    @Override
    public Courier getByid(long id) {
        Object[] params = {id};
        List<Courier> couriers = jdbcTemplate.query(selectById, new CourierRowMapper(), params);
        return couriers.isEmpty() ? null : couriers.get(0);
    }

    @Override
    public List<Courier> getAll() {
        return jdbcTemplate.query(selectAll, new CourierRowMapper());
    }

    @Override
    public CourierUpdateDto update(CourierUpdateDto courier, long id, boolean isEnabled, boolean isActive) {
        SqlParameterSource courierParams = new MapSqlParameterSource()
                .addValue("name", courier.getName())
                .addValue("surname", courier.getSurname())
                .addValue("phone", courier.getBirthday())
                .addValue("birthday", courier.getBirthday())
                .addValue("userStatus", isEnabled)
                .addValue("courierStatus", isActive)
                .addValue("id", id);
        namedParameterJdbcTemplate.update(updateCourier, courierParams);

        return courier;
    }

    @Override
    public List<Courier> getByNameSurname(String search) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("search", search);

        return namedParameterJdbcTemplate.query(filterNameQuery, params, new CourierRowMapper());
    }


}
