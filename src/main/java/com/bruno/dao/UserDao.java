package com.bruno.dao;

import com.bruno.model.User;

import java.util.List;

public interface UserDao {

    Integer addUser(User user);

    void removeUserById(Integer id);

    User getUserById(Integer id);

    List<User> listAllUsers();
}
