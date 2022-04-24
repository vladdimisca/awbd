package com.awbd.project.service.impl;

import com.awbd.project.error.ErrorMessage;
import com.awbd.project.error.exception.ResourceNotFoundException;
import com.awbd.project.model.Appointment;
import com.awbd.project.model.Car;
import com.awbd.project.model.Job;
import com.awbd.project.model.security.User;
import com.awbd.project.repository.AppointmentRepository;
import com.awbd.project.service.AppointmentService;
import com.awbd.project.service.CarService;
import com.awbd.project.service.JobService;
import com.awbd.project.service.UserService;
import com.awbd.project.service.security.JpaUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final JpaUserDetailsService jpaUserDetailsService;
    private final UserService userService;
    private final JobService jobService;
    private final CarService carService;

    @Override
    public Appointment create(Appointment appointment) {
        checkAppointmentNotExisting(appointment);

        User user = userService.getByEmail(jpaUserDetailsService.getCurrentUserPrincipal().getUsername());
        Job job = jobService.getById(appointment.getCar().getId());
        Car car = carService.getById(appointment.getJob().getId());

        appointment.setUser(user);
        appointment.setCar(car);
        appointment.setJob(job);

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
