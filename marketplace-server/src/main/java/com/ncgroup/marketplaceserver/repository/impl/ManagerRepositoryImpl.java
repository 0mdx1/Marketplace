package com.ncgroup.marketplaceserver.repository.impl;

import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.model.mapper.UserRowMapper;
import com.ncgroup.marketplaceserver.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@PropertySource("classpath:database/queries.properties")
public class ManagerRepositoryImpl implements ManagerRepository {
    private JdbcTemplate jdbcTemplate;

    @Value("${manager.find-by-id}")
    private String selectById;

    @Value("${manager.find-all}")
    private String selectAll;

    @Autowired
    ManagerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getById(int id) {
        Object[] params = {id};
        List<User> managers = jdbcTemplate.query(selectById, new UserRowMapper(), params);
        return managers.isEmpty() ? null : managers.get(0);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(selectAll, new UserRowMapper());
    }
}
