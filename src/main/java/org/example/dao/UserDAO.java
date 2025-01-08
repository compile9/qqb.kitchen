package org.example.dao;

import org.example.model.User;
import org.springframework.stereotype.Repository;

public interface UserDAO {
    void saveUser(User user);
    User getByEmail(String email);
}
