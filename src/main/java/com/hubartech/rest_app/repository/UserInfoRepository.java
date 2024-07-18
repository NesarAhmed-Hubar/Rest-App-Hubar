package com.hubartech.rest_app.repository;

import com.hubartech.rest_app.model.UserInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserInfoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(UserInfo userInfo) {
        if (userInfo.getId() == null) {
            String sql = "INSERT INTO user_info (user_id) VALUES (?)";
            jdbcTemplate.update(sql, userInfo.getUser().getId());
        } else {
            String sql = "UPDATE user_info SET user_id = ? WHERE id = ?";
            jdbcTemplate.update(sql, userInfo.getUser().getId(), userInfo.getId());
        }
    }

//    public UserInfo findByUser(User user) {
//        String sql = "SELECT * FROM user_info WHERE user_id = ?";
//        UserInfo userInfo = jdbcTemplate.queryForObject(sql, new UserInfoRowMapper(user), user.getId());
//        String sqlForListOfAddress = "SELECT * FROM user_info_address WHERE user_info_id = ?";
//        List<Address > addressList = Collections.singletonList(jdbcTemplate.queryForObject(sql, new AddressRepository.AddressRowMapper(), userInfo.getId()));
//        userInfo.setAddressList(addressList);
//        return userInfo;
//    }

//    public static class UserInfoRowMapper implements RowMapper<UserInfo> {
//        private final User user;
//        public UserInfoRowMapper(User user) {
//            this.user = user;
//        }
//        @Override
//        public UserInfo mapRow(ResultSet resultSet, int rowNum) throws SQLException {
//            UserInfo userInfo = new UserInfo();
//            userInfo.setId(resultSet.getInt("id"));
//            userInfo.setUser(user);
//            return userInfo;
//        }
//    }
}