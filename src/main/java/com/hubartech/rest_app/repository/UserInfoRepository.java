package com.hubartech.rest_app.repository;

import com.hubartech.rest_app.model.Address;
import com.hubartech.rest_app.model.User;
import com.hubartech.rest_app.model.UserInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserInfoRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserInfoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(UserInfo userInfo) {
        String sql = "INSERT INTO user_info (user_id) VALUES (?)";
        jdbcTemplate.update(sql, userInfo.getUser().getId());
    }

    public UserInfo findByUser(User user) {
        String sql = "SELECT * FROM user_info WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, new UserInfoRowMapper(user, jdbcTemplate), user.getId());
    }

    public static class UserInfoRowMapper implements RowMapper<UserInfo> {
        private final User user;
        private final JdbcTemplate jdbcTemplate;

        public UserInfoRowMapper(User user, JdbcTemplate jdbcTemplate) {
            this.user = user;
            this.jdbcTemplate = jdbcTemplate;
        }

        @Override
        public UserInfo mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(resultSet.getInt("id"));
            userInfo.setUser(user);
            String sqlForListOfAddress = "SELECT a.* FROM user_info_address ua LEFT JOIN address a ON " +
                    "ua.address_id = a.id WHERE ua.user_info_id = ?";
            List<Address> addressList = jdbcTemplate.query(sqlForListOfAddress, new AddressRepository.AddressRowMapper(),
                    userInfo.getId());
            userInfo.setAddressList(addressList);
            return userInfo;
        }
    }
}