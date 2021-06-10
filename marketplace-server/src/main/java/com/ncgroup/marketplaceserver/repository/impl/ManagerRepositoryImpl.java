package com.ncgroup.marketplaceserver.repository.impl;

import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.model.mapper.UserRowMapper;
import com.ncgroup.marketplaceserver.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@PropertySource("classpath:database/queries.properties")
public class ManagerRepositoryImpl implements ManagerRepository {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${manager.find-by-id}")
    private String selectById;

    @Value("${manager.update-person}")
    private String updateManagerPerson;

    @Value("${manager.update-credentials}")
    private String updateManagerCredentials;

    @Value("${manager.find-by-name-surname}")
    private String filterNameQuery;

    @Value("${manager.number-of-rows}")
    private String selectNumberOfRows;

    @Value("${manager.number-of-rows-all}")
    private String selectNumberOfRowsAll;

    @Value("${manager.find-by-name-surname-all}")
    private String filterNameQueryAll;

    @Autowired
    ManagerRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public User getById(long id) {
        Object[] params = {id};
        List<User> managers = jdbcTemplate.query(selectById, new UserRowMapper(), params);
        return managers.isEmpty() ? null : managers.get(0);
    }

    @Transactional
    @Override
    public User update(User manager, long id) {
        SqlParameterSource credentialsParams = new MapSqlParameterSource()
                .addValue("userStatus", manager.isEnabled())
                .addValue("email", manager.getEmail())
                .addValue("id", id);
        namedParameterJdbcTemplate.update(updateManagerCredentials, credentialsParams);


        SqlParameterSource personParams = new MapSqlParameterSource()
                .addValue("name", manager.getName())
                .addValue("surname", manager.getSurname())
                .addValue("phone", manager.getPhone())
                .addValue("birthday", manager.getBirthday())
                .addValue("id", id);
        namedParameterJdbcTemplate.update(updateManagerPerson, personParams);
        return manager;
    }

    @Override
    public List<User> getByNameSurname(String search, boolean status, int page) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("search", search)
                .addValue("status", status)
                .addValue("page", page);
        return namedParameterJdbcTemplate.query(filterNameQuery, params, new UserRowMapper());
    }

    @Override
    public int getNumberOfRows(String search, boolean status) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("search", search)
                .addValue("status", status);
        return namedParameterJdbcTemplate.queryForObject(selectNumberOfRows, params, Integer.class);
    }

    @Override
    public int getNumberOfRowsAll(String search) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("search", search);
        return namedParameterJdbcTemplate.queryForObject(selectNumberOfRowsAll, params, Integer.class);
    }

    @Override
    public List<User> getByNameSurnameAll(String search, int page) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("search", search)
                .addValue("page", page);
        return namedParameterJdbcTemplate.query(filterNameQueryAll, params, new UserRowMapper());
    }


}
