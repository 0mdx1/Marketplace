package com.ncgroup.marketplaceserver.repository.impl;

import java.util.List;

import com.ncgroup.marketplaceserver.model.Courier;
import com.ncgroup.marketplaceserver.model.dto.UserDto;
import com.ncgroup.marketplaceserver.model.mapper.CourierRowMapper;
import com.ncgroup.marketplaceserver.repository.CourierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ncgroup.marketplaceserver.model.Role;
import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.model.mapper.UserRowMapper;
import com.ncgroup.marketplaceserver.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@PropertySource("classpath:database/queries.properties")
public class CourierRepositoryImpl implements CourierRepository {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

/*    @Value("${courier.insert}")
    private String insertCourierQuery;*/
    @Value("${courier.find-by-id}")
    private String selectById;

    @Value("${courier.find-all}")
    private String selectAll;



    @Autowired
    public CourierRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public Courier save(Courier courier) {
        SqlParameterSource userParameters = new MapSqlParameterSource()
                .addValue("id", courier.getUser().getId())
                .addValue("status", courier.isStatus());

        //namedParameterJdbcTemplate.update(insertCourierQuery, userParameters);
        return courier;
    }

    @Override
    public Courier getByid(int id) {
        Object[] params = {id};
        List<Courier> couriers = jdbcTemplate.query(selectById, new CourierRowMapper(), params);
        return couriers.isEmpty() ? null : couriers.get(0);
    }

    @Override
    public List<Courier> getAll() {
        return jdbcTemplate.query(selectAll, new CourierRowMapper());
    }


}
