package com.ncgroup.marketplaceserver.repository.impl;

import java.util.List;

import com.ncgroup.marketplaceserver.model.Courier;
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

    @Value("${user.find-all}")
    private String findAllQuery;

    @Value("${user.find-by-email}")
    private String findByEmailQuery;

    @Value("${user.find-by-id}")
    private String findByIdQuery;

    @Value("${role.find}")
    private String findRoleQuery;

    @Value("${user.insert-credentials}")
    private String insertCredentialsQuery;

    @Value("${user.insert}")
    private String insertUserQuery;

    @Value("${user.update-last-failed-auth}")
    private String updateLastFailedAuthQuery;

    @Value("${user.enable}")
    private String enableUserQuery;

    @Value("${user.find-by-auth-link}")
    private String findByAuthLinkQuery;

    @Value("${user.update-auth-link}")
    private String updateAuthLinkQuery;

    @Value("${user.update-password}")
    private String updatePasswordQuery;

    @Value("${user.delete-auth-link}")
    private String deleteAuthLinkQuery;

    @Value("${user.delete-by-id}")
    private String deleteByIdQuery;

    @Value("${user.delete-credentials-by-email}")
    private String deleteCredByEmailQuery;




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
                .addValue("status", courier.getStatus());

        namedParameterJdbcTemplate.update(insertUserQuery, userParameters);
        return courier;
    }


}
