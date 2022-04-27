package com.awbd.project.repository;

import com.awbd.project.model.Appointment;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("h2")
@Rollback(false)
@Slf4j
class AppointmentRepositoryIT {

    private static final String USER_EMAIL = "random@gmail.com";

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    @DisplayName("Find all appointments by email")
    void findAllByEmail() {
        Pageable page = PageRequest.of(0, 2);
        Page<Appointment> appointmentsPage = appointmentRepository.findAllByEmail(USER_EMAIL, page);

        assertEquals(3, appointmentsPage.getTotalElements());
        assertEquals(2, appointmentsPage.getNumberOfElements());

        appointmentsPage.getContent().forEach(appointment -> log.info("Appointment id={} and startDate={}", appointment.getId(), appointment.getStartTime()));
    }
}