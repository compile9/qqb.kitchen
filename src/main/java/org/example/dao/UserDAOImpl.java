package org.example.dao;

import org.example.model.User;
import org.example.model.UserMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserDAOImpl implements UserDAO {
    JdbcTemplate jdbc;

    public UserDAOImpl(DataSource dataSource) {
        jdbc = new JdbcTemplate(dataSource);
    }

    public void save(User user) {
        String sql = "INSERT INTO users (username, email, password, created_at, profile_picture) VALUES (?, ?, ?, ?, ?)";
        jdbc.update(sql, user.getUsername(), user.getEmail(), user.getPassword(), user.getCreatedAt(), user.getProfilePicture());
    }

    public User getByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            return jdbc.queryForObject(sql, new UserMapper(), email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
