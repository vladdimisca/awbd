package com.awbd.project.service;

import com.awbd.project.model.Appointment;

import java.util.List;

public interface AppointmentService {
    Appointment create(Appointment appointment);

    Appointment update(Long id, Appointment car);

    Appointment getById(Long id);

    List<Appointment> getAll();

    void deleteById(Long id);
}
