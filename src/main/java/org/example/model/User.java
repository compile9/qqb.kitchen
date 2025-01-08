package org.example.model;

import java.time.LocalDateTime;

public class User {
    private Long id;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private String profilePicture;

    public User(Long id, String username, String email, String password, LocalDateTime createdAt, String profilePicture) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.profilePicture = profilePicture;
    }

    // constructor for registration
    public User(String username, String email, String password) {
        this(null, username, email, password, LocalDateTime.now(), null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Object getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
