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
import org.springframework.data.relational.core.sql.In;
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

    @Value("${courier.update}")
    private String updateCourier;

    @Value("${courier.update-person}")
    private String updatePerson;

    @Value("${courier.update-credentials}")
    private String updateCredentials;

    @Value("${courier.find-by-name-surname}")
    private String filterNameQuery;

    @Value("${courier.find-by-name-surname-all}")
    private String filterNameQueryAll;

    @Value("${courier.number-of-rows}")
    private String selectNumberOfRows;

    @Value("${courier.number-of-rows-all}")
    private String selectNumberOfRowsAll;


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
    public CourierUpdateDto update(CourierUpdateDto courier, long id, boolean isEnabled, boolean isActive) {
        SqlParameterSource personParams = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("name", courier.getName())
                .addValue("surname", courier.getSurname())
                .addValue("phone", courier.getPhone())
                .addValue("birthday", courier.getBirthday());
        namedParameterJdbcTemplate.update(updatePerson, personParams);

        SqlParameterSource credentialsParams = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("is_enabled", isEnabled);
        namedParameterJdbcTemplate.update(updateCredentials, credentialsParams);

        SqlParameterSource courierParams = new MapSqlParameterSource()
                .addValue("is_active", isActive)
                .addValue("id", id);
        namedParameterJdbcTemplate.update(updateCourier, courierParams);

        return courier;
    }

    @Override
    public List<Courier> getByNameSurname(String search, boolean is_enabled, boolean is_active, int page) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("search", search)
                .addValue("is_enabled", is_enabled)
                .addValue("is_active", is_active)
                .addValue("page", page);
        return namedParameterJdbcTemplate.query(filterNameQuery, params, new CourierRowMapper());
    }

    @Override
    public List<Courier> getByNameSurnameAll(String search, int page) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("search", search)
                .addValue("page", page);
        return namedParameterJdbcTemplate.query(filterNameQueryAll, params, new CourierRowMapper());
    }

    @Override
    public int getNumberOfRows(String search, boolean is_enabled, boolean is_active) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("search", search)
                .addValue("is_enabled", is_enabled)
                .addValue("is_active", is_active);
        return namedParameterJdbcTemplate.queryForObject(selectNumberOfRows, params, Integer.class);
    }

    @Override
    public int getNumberOfRowsAll(String search) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("search", search);
        return namedParameterJdbcTemplate.queryForObject(selectNumberOfRowsAll, params, Integer.class);
    }


}
