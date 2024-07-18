package com.hubartech.rest_app.repository;

import com.hubartech.rest_app.enums.UserRole;
import com.hubartech.rest_app.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findById(Integer id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), email);
    }

    public User findByPhoneNumber(String phoneNumber) {
        String sql = "SELECT * FROM users WHERE phone_number = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), phoneNumber);
    }

    public void save(User user) {
        if (user.getId() == null) {
            String sql = "INSERT INTO users (email, password, phone_number, role) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getPhoneNumber(), user.getRole().name());
        } else {
            String sql = "UPDATE users SET email = ?, password = ?, phone_number = ?, role = ? WHERE id = ?";
            jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getPhoneNumber(), user.getRole().name(), user.getId());
        }
    }

    public static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setPhoneNumber(resultSet.getString("phone_number"));
            user.setRole(UserRole.valueOf(resultSet.getString("role")));
            return user;
        }
    }
}