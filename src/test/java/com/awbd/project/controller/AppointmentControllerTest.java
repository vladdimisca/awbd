package com.awbd.project.controller;

import com.awbd.project.model.*;
import com.awbd.project.model.security.User;
import com.awbd.project.service.impl.AppointmentServiceImpl;
import com.awbd.project.service.impl.CarServiceImpl;
import com.awbd.project.service.impl.JobServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("h2")
class AppointmentControllerTest {

    private static final Long ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarServiceImpl carService;

    @MockBean
    private JobServiceImpl jobService;

    @MockBean
    private AppointmentServiceImpl appointmentService;

    @Test
    @DisplayName("Create appointment - success")
    void create_success() throws Exception {
        Appointment appointment = getEmployee();
        Appointment savedAppointment = getSavedAppointment();

        when(appointmentService.create(appointment)).thenReturn(savedAppointment);

        mockMvc.perform(post("/appointments").flashAttr("appointment", appointment))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/appointments"));
    }

    @Test
    @DisplayName("Update appointment - success")
    void update_success() throws Exception {
        Appointment updatedAppointment = getSavedAppointment();

        when(appointmentService.update(ID, updatedAppointment)).thenReturn(updatedAppointment);

        mockMvc.perform(put("/appointments/{id}", ID).flashAttr("appointment", updatedAppointment))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/appointments"));
    }

    @Test
    @DisplayName("Get appointment by id - success")
    void getById_success() throws Exception {
        Appointment appointment = getSavedAppointment();

        when(appointmentService.getById(ID)).thenReturn(appointment);

        mockMvc.perform(get("/appointments/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(view().name("appointment-info"));
    }

    @Test
    @DisplayName("Get all appointments - success")
    void getAll_success() throws Exception {
        when(appointmentService.getAll(PageRequest.of(0, 15, Sort.by("startTime"))))
                .thenReturn(new PageImpl<>(List.of(getSavedAppointment())));

        mockMvc.perform(get("/appointments"))
                .andExpect(status().isOk())
                .andExpect(view().name("appointments"));
    }

    @Test
    @DisplayName("Delete appointment by id - success")
    void delete_success() throws Exception {
        mockMvc.perform(delete("/appointments/{id}", ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/appointments"));
    }

    @Test
    @DisplayName("Get appointment form - success")
    void getForm_success() throws Exception {
        when(jobService.getAll()).thenReturn(Collections.emptyList());
        when(carService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/appointments/form"))
                .andExpect(status().isOk())
                .andExpect(view().name("appointment-form"));
    }

    @Test
    @DisplayName("Get update appointment form - success")
    void getUpdateForm_success() throws Exception {
        when(appointmentService.getById(ID)).thenReturn(getSavedAppointment());
        when(jobService.getAll()).thenReturn(Collections.emptyList());
        when(carService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/appointments/form/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(view().name("update-appointment-form"));
    }

    private Appointment getEmployee() {
        Car car = new Car();
        car.setId(2L);
        car.setType(CarType.VAN);
        car.setLicensePlate("abc");
        Job job = new Job();
        job.setId(3L);
        job.setType(JobType.INTERIOR_AND_EXTERIOR);
        UserDetails userDetails = UserDetails.builder()
                .phoneNumber("0755112299")
                .build();
        User user = new User();
        user.setId(4L);
        user.setUserDetails(userDetails);

        Appointment appointment = new Appointment();
        appointment.setStartTime(LocalDateTime.now().plusDays(2));
        appointment.setCar(car);
        appointment.setJob(job);
        appointment.setUser(user);

        return appointment;
    }

    private Appointment getSavedAppointment() {
        Appointment savedAppointment = getEmployee();
        savedAppointment.setId(ID);

        return savedAppointment;
    }

}