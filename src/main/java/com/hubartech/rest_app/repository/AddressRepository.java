package com.hubartech.rest_app.repository;

import com.hubartech.rest_app.model.Address;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class AddressRepository {

    private final JdbcTemplate jdbcTemplate;

    public AddressRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer create(Address address) {
        String sql = "INSERT INTO address (line1, line2, city, state, postal_code, country) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
        return jdbcTemplate.queryForObject(sql, Integer.class, address.getLine1(), address.getLine2(),
                address.getCity(), address.getState(), address.getPostalCode(), address.getCountry());
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM address WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Address FindById(Integer id) {
        String sql = "SELECT * FROM address WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new AddressRowMapper(), id);
    }

    public void update(Address address) {
        String sql = "UPDATE address SET city = ?, country = ?, line1 = ?, line2 = ?, postal_code = ?, state = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(sql, address.getCity(), address.getCountry(), address.getLine1(), address.getLine2(),
                address.getPostalCode(), address.getState(), address.getId());
    }

    public static class AddressRowMapper implements RowMapper<Address> {
        @Override
        public Address mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Address address = new Address();
            address.setId(resultSet.getInt("id"));
            address.setCity(resultSet.getString("city"));
            address.setCountry(resultSet.getString("country"));
            address.setLine1(resultSet.getString("line1"));
            address.setLine2(resultSet.getString("line2"));
            address.setPostalCode(resultSet.getString("postal_code"));
            address.setState(resultSet.getString("state"));
            address.setCountry(resultSet.getString("country"));
            return address;
        }
    }
}