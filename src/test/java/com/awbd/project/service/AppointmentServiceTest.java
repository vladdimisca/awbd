package com.awbd.project.service;

import com.awbd.project.error.exception.ForbiddenActionException;
import com.awbd.project.error.exception.ResourceNotFoundException;
import com.awbd.project.model.*;
import com.awbd.project.model.security.User;
import com.awbd.project.repository.AppointmentRepository;
import com.awbd.project.service.impl.AppointmentServiceImpl;
import com.awbd.project.service.security.JpaUserDetailsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    private static final Long ID = 1L;
    private static final Long JOB_ID = 2L;
    private static final Long CAR_ID = 3L;
    private static final Long EMPLOYEE_ID = 4L;
    private static final String USER_EMAIL = "test";

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private JpaUserDetailsService jpaUserDetailsService;

    @Mock
    private UserService userService;

    @Mock
    private JobService jobService;

    @Mock
    private CarService carService;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Test
    @DisplayName("Create appointment - success")
    void create_success() {
        Appointment appointment = getAppointment();
        Appointment savedAppointment = getSavedAppointment();

        when(jobService.getById(JOB_ID)).thenReturn(appointment.getJob());
        when(carService.getById(CAR_ID)).thenReturn(appointment.getCar());
        when(employeeService.getAll()).thenReturn(List.of(getEmployee()));
        when(userService.getByEmail(USER_EMAIL)).thenReturn(getUser());
        when(jpaUserDetailsService.getCurrentUserPrincipal()).thenReturn(
                new org.springframework.security.core.userdetails.User(USER_EMAIL, "pass", new HashSet<>()));
        when(appointmentRepository.save(appointment)).thenReturn(savedAppointment);

        Appointment resultedAppointment = appointmentService.create(appointment);

        assertNotNull(resultedAppointment);
        assertEquals(savedAppointment.getId(), resultedAppointment.getId());
        assertEquals(savedAppointment.getStartTime(), resultedAppointment.getStartTime());
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    @DisplayName("Get appointment by id - success")
    void getById_success() {
        Appointment appointment = getSavedAppointment();

        when(appointmentRepository.findById(ID)).thenReturn(Optional.of(appointment));
        when(jpaUserDetailsService.hasAuthority("ROLE_ADMIN")).thenReturn(true);

        Appointment resultedAppointment = appointmentService.getById(ID);

        assertNotNull(resultedAppointment);
        assertEquals(appointment.getId(), resultedAppointment.getId());
        assertEquals(appointment.getStartTime(), resultedAppointment.getStartTime());
        verify(appointmentRepository, times(1)).findById(ID);
        verify(jpaUserDetailsService, times(1)).hasAuthority("ROLE_ADMIN");
    }

    @Test
    @DisplayName("Get appointment by id - not found - failure")
    void getById_notFound_failure() {
        when(appointmentRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> appointmentService.getById(ID));
        verify(appointmentRepository, times(1)).findById(ID);
    }

    @Test
    @DisplayName("Get appointment by id - forbidden - failure")
    void getById_forbidden_failure() {
        Appointment appointment = getSavedAppointment();

        when(appointmentRepository.findById(ID)).thenReturn(Optional.of(appointment));
        when(jpaUserDetailsService.hasAuthority("ROLE_ADMIN")).thenReturn(false);
        when(jpaUserDetailsService.getCurrentUserPrincipal()).thenReturn(
                new org.springframework.security.core.userdetails.User("random user", "pass", new HashSet<>()));

        assertThrows(ForbiddenActionException.class, () -> appointmentService.getById(ID));
        verify(appointmentRepository, times(1)).findById(ID);
    }

    @Test
    @DisplayName("Get all appointments - success")
    void getAll_success() {
        Pageable pageable = PageRequest.of(1, 5, Sort.by("startTime").descending());

        when(jpaUserDetailsService.hasAuthority("ROLE_ADMIN")).thenReturn(true);
        when(appointmentRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(getSavedAppointment())));

        Page<Appointment> appointmentsPage = appointmentService.getAll(pageable);

        assertNotNull(appointmentsPage);
        assertEquals(1, appointmentsPage.getTotalElements());
        verify(appointmentRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Get all appointments by user - success")
    void getAllByUser_success() {
        Pageable pageable = PageRequest.of(1, 5, Sort.by("startTime").descending());

        when(jpaUserDetailsService.hasAuthority("ROLE_ADMIN")).thenReturn(false);
        when(jpaUserDetailsService.getCurrentUserPrincipal()).thenReturn(
                new org.springframework.security.core.userdetails.User(USER_EMAIL, "pass", new HashSet<>()));
        when(appointmentRepository.findAllByEmail(USER_EMAIL, pageable)).thenReturn(new PageImpl<>(List.of(getSavedAppointment())));

        Page<Appointment> appointmentsPage = appointmentService.getAll(pageable);

        assertNotNull(appointmentsPage);
        assertEquals(1, appointmentsPage.getTotalElements());
        verify(appointmentRepository, times(1)).findAllByEmail(USER_EMAIL, pageable);
    }

    @Test
    @DisplayName("Update appointment by id - success")
    void update_success() {
        Appointment appointment = getSavedAppointment();
        Appointment updatedAppointment = getSavedAppointment();
        updatedAppointment.setStartTime(LocalDateTime.now());

        when(appointmentRepository.findById(ID)).thenReturn(Optional.of(appointment));
        when(jpaUserDetailsService.hasAuthority("ROLE_ADMIN")).thenReturn(true);
        when(jpaUserDetailsService.getCurrentUserPrincipal()).thenReturn(
                new org.springframework.security.core.userdetails.User(USER_EMAIL, "pass", new HashSet<>()));
        when(jobService.getById(JOB_ID)).thenReturn(updatedAppointment.getJob());
        when(carService.getById(CAR_ID)).thenReturn(updatedAppointment.getCar());
        when(employeeService.getAll()).thenReturn(List.of(getEmployee()));
        when(appointmentRepository.save(appointment)).thenReturn(updatedAppointment);

        Appointment resultedAppointment = appointmentService.update(ID, updatedAppointment);

        assertNotNull(resultedAppointment);
        assertEquals(updatedAppointment.getId(), resultedAppointment.getId());
        assertEquals(updatedAppointment.getStartTime(), resultedAppointment.getStartTime());
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    @DisplayName("Delete appointment by id - success")
    void delete_success() {
        Appointment appointment = getSavedAppointment();

        when(appointmentRepository.findById(ID)).thenReturn(Optional.of(appointment));
        when(jpaUserDetailsService.hasAuthority("ROLE_ADMIN")).thenReturn(true);

        appointmentService.deleteById(ID);

        verify(appointmentRepository, times(1)).findById(ID);
        verify(jpaUserDetailsService, times(1)).hasAuthority("ROLE_ADMIN");
    }

    private Appointment getAppointment() {
        Appointment appointment = new Appointment();
        appointment.setCar(getCar());
        appointment.setJob(getJob());
        appointment.setStartTime(LocalDateTime.now());
        appointment.setUser(getUser());
        appointment.setEmployees(Collections.emptyList());

        return appointment;
    }

    private Appointment getSavedAppointment() {
        Appointment appointment = getAppointment();
        appointment.setId(ID);

        return appointment;
    }

    private Job getJob() {
        Job job = new Job();
        job.setId(JOB_ID);
        job.setType(JobType.EXTERIOR);
        job.setCarType(CarType.REGULAR);
        job.setPrice(15.0);
        job.setNumberOfEmployees(1);
        job.setDurationMinutes(15L);

        return job;
    }

    private Car getCar() {
        Car car = new Car();
        car.setId(CAR_ID);
        car.setType(CarType.REGULAR);
        car.setCreatedAt(LocalDateTime.now());
        car.setLicensePlate("test");
        car.setUser(getUser());

        return car;
    }

    private User getUser() {
        return User.builder()
                .email(USER_EMAIL)
                .build();
    }

    private Employee getEmployee() {
        Employee employee = new Employee();
        employee.setId(EMPLOYEE_ID);
        employee.setEmail("test@email");
        employee.setPhoneNumber("test_phone");
        employee.setFirstName("testf");
        employee.setLastName("testl");
        employee.setAppointments(Collections.emptyList());

        return employee;
    }
}