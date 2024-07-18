package com.hubartech.rest_app.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoAddressRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserInfoAddressRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(int userInfoId, int addressId) {
        String sql = "INSERT INTO user_info_address (user_info_id, address_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, userInfoId, addressId);
    }
}