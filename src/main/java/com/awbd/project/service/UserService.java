package com.awbd.project.service;

import com.awbd.project.model.security.User;

import java.util.List;

public interface UserService {
    User create(User user);

    User update(Long id, User user);

    User getById(Long id);

    List<User> getAll();

    void deleteById(Long id);
}
