package com.awbd.project.service;

import com.awbd.project.model.security.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User create(User user);

    User update(Long id, User user);

    User getById(Long id);

    User getByEmail(String email);

    Page<User> getAll(Pageable pageable);

    void deleteById(Long id);
}
