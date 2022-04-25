package com.awbd.project.service.impl;

import com.awbd.project.error.ErrorMessage;
import com.awbd.project.error.exception.ConflictException;
import com.awbd.project.error.exception.ResourceNotFoundException;
import com.awbd.project.model.security.Authority;
import com.awbd.project.model.security.User;
import com.awbd.project.repository.security.AuthorityRepository;
import com.awbd.project.repository.security.UserRepository;
import com.awbd.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User create(User user) {
        checkUserNotExisting(user);

        Authority authority = authorityRepository.findByRole("ROLE_GUEST");
        user.setAuthorities(Collections.singleton(authority));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        User existingUser = getById(id);
        if (!existingUser.getEmail().equals(user.getEmail())) {
            checkEmailNotExisting(user);
        }
        if (!existingUser.getUserDetails().getPhoneNumber().equals(user.getUserDetails().getPhoneNumber())) {
            checkPhoneNumberNotExisting(user);
        }

        copyValues(existingUser, user);

        return userRepository.save(existingUser);
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND, "user", id));
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND, "user", email));
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        User user = getById(id);
        userRepository.delete(user);
    }

    private void checkUserNotExisting(User user) {
       if (userRepository.existsByEmailOrPhoneNumber(user.getEmail(), user.getUserDetails().getPhoneNumber())) {
            throw new ConflictException(ErrorMessage.ALREADY_EXISTS, "user", "email or phone number");
        }
    }

    private void checkEmailNotExisting(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ConflictException(ErrorMessage.ALREADY_EXISTS, "user", "email");
        }
    }

    private void checkPhoneNumberNotExisting(User user) {
        if (userRepository.existsByPhoneNumber(user.getUserDetails().getPhoneNumber())) {
            throw new ConflictException(ErrorMessage.ALREADY_EXISTS, "user", "phone number");
        }
    }

    private void copyValues(User to, User from) {
        to.getUserDetails().setFirstName(from.getUserDetails().getFirstName());
        to.getUserDetails().setLastName(from.getUserDetails().getLastName());
        to.getUserDetails().setSex(from.getUserDetails().getSex());
        to.getUserDetails().setPhoneNumber(from.getUserDetails().getPhoneNumber());
        to.setEmail(from.getEmail());
        to.setPassword(bCryptPasswordEncoder.encode(from.getPassword()));
    }
}
