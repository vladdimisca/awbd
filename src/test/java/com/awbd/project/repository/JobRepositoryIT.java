package com.awbd.project.repository;

import com.awbd.project.model.CarType;
import com.awbd.project.model.JobType;
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
class JobRepositoryIT {

    @Autowired
    private JobRepository jobRepository;

    @Test
    @DisplayName("Check if job exists by type and job type")
    public void existsByTypeAndJobType() {
        boolean exists = jobRepository.existsByTypeAndCarType(JobType.INTERIOR, CarType.VAN);

        assertTrue(exists);
    }
}