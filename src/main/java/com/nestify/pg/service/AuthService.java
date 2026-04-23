package com.nestify.pg.service;

import com.nestify.pg.entity.User;
import java.util.ArrayList;
import java.util.List;

public class AuthService {

    private static List<User> users = new ArrayList<>();

    static {
        users.add(new User("admin", "admin123", "ADMIN"));
        users.add(new User("tenant1", "1234", "TENANT"));
    }

    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) &&
                user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}