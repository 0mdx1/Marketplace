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

import java.util.List;

@Repository
@PropertySource("classpath:database/queries.properties")
public class ManagerRepositoryImpl implements ManagerRepository {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${manager.find-by-id}")
    private String selectById;

    @Value("${manager.find-all}")
    private String selectAll;

    @Value("${manager.update}")
    private String updateManager;

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

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(selectAll, new UserRowMapper());
    }

    @Override
    public User update(User manager, long id) {
        SqlParameterSource managerParams = new MapSqlParameterSource()
                .addValue("name", manager.getName())
                .addValue("surname", manager.getSurname())
                .addValue("phone", manager.getPhone())
                .addValue("birthday", manager.getBirthday())
                .addValue("userStatus", manager.isEnabled());
        namedParameterJdbcTemplate.update(updateManager, managerParams);
        return manager;
    }


}
