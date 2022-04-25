package com.awbd.project.service.impl;

import com.awbd.project.error.ErrorMessage;
import com.awbd.project.error.exception.ConflictException;
import com.awbd.project.error.exception.ForbiddenActionException;
import com.awbd.project.error.exception.ResourceNotFoundException;
import com.awbd.project.model.Appointment;
import com.awbd.project.model.Car;
import com.awbd.project.model.Employee;
import com.awbd.project.model.Job;
import com.awbd.project.model.security.User;
import com.awbd.project.repository.AppointmentRepository;
import com.awbd.project.service.*;
import com.awbd.project.service.security.JpaUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final JpaUserDetailsService jpaUserDetailsService;
    private final UserService userService;
    private final JobService jobService;
    private final CarService carService;
    private final EmployeeService employeeService;

    @Override
    public Appointment create(Appointment appointment) {
        Job job = jobService.getById(appointment.getJob().getId());
        List<Employee> employees =
                getFreeEmployees(job.getNumberOfEmployees(), appointment.getStartTime(), job.getDurationMinutes());

        User user = userService.getByEmail(jpaUserDetailsService.getCurrentUserPrincipal().getUsername());
        Car car = carService.getById(appointment.getCar().getId());

        appointment.setEmployees(employees);
        appointment.setUser(user);
        appointment.setCar(car);
        appointment.setJob(job);

        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment update(Long id, Appointment appointment) {
        Appointment existingAppointment = getById(id);
        if (!existingAppointment.getUser().getEmail().equals(jpaUserDetailsService.getCurrentUserPrincipal().getUsername())) {
            throw new ForbiddenActionException(ErrorMessage.FORBIDDEN);
        }

        Job job = jobService.getById(appointment.getJob().getId());
        List<Employee> employees =
                getFreeEmployees(job.getNumberOfEmployees(), appointment.getStartTime(), job.getDurationMinutes());

        existingAppointment.setEmployees(employees);
        existingAppointment.setStartTime(appointment.getStartTime());
        existingAppointment.setCar(carService.getById(appointment.getCar().getId()));
        existingAppointment.setJob(jobService.getById(appointment.getJob().getId()));

        return appointmentRepository.save(existingAppointment);
    }

    @Override
    public Appointment getById(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND, "appointment", id));
        boolean isAdmin = jpaUserDetailsService.hasAuthority("ROLE_ADMIN");
        if (!isAdmin && !appointment.getUser().getEmail().equals(jpaUserDetailsService.getCurrentUserPrincipal().getUsername())) {
            throw new ForbiddenActionException(ErrorMessage.FORBIDDEN);
        }

        return appointment;
    }

    @Override
    public List<Appointment> getAll() {
        boolean isAdmin = jpaUserDetailsService.hasAuthority("ROLE_ADMIN");
        if (isAdmin) {
            return appointmentRepository.findAll();
        }
        return appointmentRepository.findAllByEmail(jpaUserDetailsService.getCurrentUserPrincipal().getUsername());
    }

    @Override
    public void deleteById(Long id) {
        Appointment appointment = getById(id);
        appointmentRepository.delete(appointment);
    }

    private List<Employee> getFreeEmployees(Integer numberOfEmployees, LocalDateTime startTime, Long durationMinutes) {
        List<Employee> freeEmployees = new ArrayList<>();
        List<Employee> allEmployees = employeeService.getAll();
        for (Employee employee : allEmployees) {
            if (numberOfEmployees.equals(freeEmployees.size())) {
                return freeEmployees;
            }

            boolean isFree = true;
            for (Appointment appointment : employee.getAppointments()) {
                LocalDateTime start = appointment.getStartTime();
                LocalDateTime end = appointment.getStartTime().plusMinutes(appointment.getJob().getDurationMinutes());
                if ((start.isAfter(startTime) && start.isBefore(startTime.plusMinutes(durationMinutes))) ||
                        (end.isAfter(startTime) && end.isBefore(startTime.plusMinutes(durationMinutes)))) {
                    isFree = false;
                    break;
                }
            }
            if (isFree) {
                freeEmployees.add(employee);
            }
        }
        if (freeEmployees.size() < numberOfEmployees) {
            throw new ConflictException(ErrorMessage.NOT_ENOUGH_RESOURCES, "employees");
        }
        return freeEmployees;
    }
}
