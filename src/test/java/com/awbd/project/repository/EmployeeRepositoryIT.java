package com.awbd.project.repository;

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
class EmployeeRepositoryIT {

    private static final String EMAIL = "random@yahoo.com";
    private static final String PHONE_NUMBER = "0765332211";

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("Check if employee exists by email or phone number")
    void existsByEmailOrPhoneNumber() {
        boolean exists = employeeRepository.existsByEmailOrPhoneNumber(EMAIL, PHONE_NUMBER);

        assertTrue(exists);
    }

    @Test
    @DisplayName("Check if employee exists by email")
    void existsByEmail() {
        boolean exists = employeeRepository.existsByEmail(EMAIL);

        assertTrue(exists);
    }

    @Test
    @DisplayName("Check if employee exists by phone number")
    void existsByPhoneNumber() {
        boolean exists = employeeRepository.existsByPhoneNumber(PHONE_NUMBER);

        assertTrue(exists);
    }

}