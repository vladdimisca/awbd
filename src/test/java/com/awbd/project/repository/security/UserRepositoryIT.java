package com.awbd.project.repository.security;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("h2")
@Rollback(false)
@Slf4j
class UserRepositoryIT {

    private static final String EMAIL = "random@gmail.com";
    private static final String PHONE_NUMBER = "0755221133";

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Check if user exists by email or phone number")
    void existsByEmailOrPhoneNumber() {
        boolean exists = userRepository.existsByEmailOrPhoneNumber(EMAIL, PHONE_NUMBER);

        assertTrue(exists);
    }

    @Test
    @DisplayName("Check if user exists by email")
    void existsByEmail() {
        boolean exists = userRepository.existsByEmail(EMAIL);

        assertTrue(exists);
    }

    @Test
    @DisplayName("Check if user exists by phone number")
    void existsByPhoneNumber() {
        boolean exists = userRepository.existsByPhoneNumber(PHONE_NUMBER);

        assertTrue(exists);
    }
}