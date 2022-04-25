package com.awbd.project.service;

import com.awbd.project.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AppointmentService {
    Appointment create(Appointment appointment);

    Appointment update(Long id, Appointment car);

    Appointment getById(Long id);

    Page<Appointment> getAll(Pageable pageable);

    void deleteById(Long id);
}
