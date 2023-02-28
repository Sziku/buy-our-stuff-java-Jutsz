package com.codecool.buyourstuff.dao;

import com.codecool.buyourstuff.model.User;

public interface UserDao {
    void add(User user);
    User find(String userName, String password);
    void clear();
    boolean isNameAvailable(String username);
}
