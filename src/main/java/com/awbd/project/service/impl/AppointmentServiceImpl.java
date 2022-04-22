package com.awbd.project.service.impl;

import com.awbd.project.error.ErrorMessage;
import com.awbd.project.error.exception.ResourceNotFoundException;
import com.awbd.project.model.Appointment;
import com.awbd.project.repository.AppointmentRepository;
import com.awbd.project.service.AppointmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Appointment create(Appointment appointment) {
        checkAppointmentNotExisting(appointment);
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment update(Long id, Appointment appointment) {
        Appointment existingAppointment = getById(id);
//        if (!existingCar.getLicensePlate().equals(car.getLicensePlate())) {
//            checkCarNotExisting(car);
//        }

        copyValues(existingAppointment, appointment);

        return appointmentRepository.save(existingAppointment);
    }

    @Override
    public Appointment getById(Long id) {
        return appointmentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND, "appointment", id));
    }

    @Override
    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        Appointment appointment = getById(id);
        appointmentRepository.delete(appointment);
    }

    private void checkAppointmentNotExisting(Appointment appointment) {
//        if (appointmentRepository.existsByLicensePlate(car.getLicensePlate())) {
//            throw new ConflictException(ErrorMessage.ALREADY_EXISTS, "car", "license plate");
//        }
    }

    private void copyValues(Appointment to, Appointment from) {
//        to.setType(from.getType());
//        to.setLicensePlate(from.getLicensePlate());
    }
}
