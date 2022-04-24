package com.awbd.project.service.impl;

import com.awbd.project.error.ErrorMessage;
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
//        if (!existingCar.getLicensePlate().equals(car.getLicensePlate())) {
//            checkCarNotExisting(car);
//        }

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
//        if (userRepository.existsByLicensePlate(car.getLicensePlate())) {
//            throw new ConflictException(ErrorMessage.ALREADY_EXISTS, "car", "license plate");
//        }
    }

    private void copyValues(User to, User from) {
//        to.set(from.getType());
//        to.setLicensePlate(from.getLicensePlate());
    }
}
