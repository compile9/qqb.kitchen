package org.example.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
            rs.getLong("id"),
            rs.getString("username"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getTimestamp("created_at"),
            rs.getString("profile_picture")
        );
    }
}
