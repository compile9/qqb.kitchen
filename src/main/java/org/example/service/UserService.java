package org.example.service;

import org.example.dao.UserDAOImpl;
import org.example.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserDAOImpl userDaoImpl;
    public UserService(UserDAOImpl userDAOImpl) {
        this.userDaoImpl = userDAOImpl;
    }

    public void register(String username, String email, String password) {
        // check if the email already exists
        User existingUser = userDaoImpl.getByEmail(email);
        if (existingUser != null) {
            throw new IllegalArgumentException("This email already exists!");
        }
        // hash the password (later)

        // save the new user to db
        User newUser = new User(username, email, password);
        userDaoImpl.saveUser(newUser);
    }

    public User login(String email, String password) {
        User user = userDaoImpl.getByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("This email doesn't exist.");
        }

        // verify the password (later)

        // login successful, return the user
        return user;
    }
}
