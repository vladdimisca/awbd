package com.awbd.project.controller;

import com.awbd.project.model.Car;
import com.awbd.project.model.CarType;
import com.awbd.project.model.security.User;
import com.awbd.project.service.impl.CarServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("h2")
class CarControllerTest {

    private static final Long ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarServiceImpl carService;

    @Test
    @DisplayName("Create car - success")
    void create_success() throws Exception {
        Car car = getCar();
        Car savedCar = getSavedCar();

        when(carService.create(car)).thenReturn(savedCar);

        mockMvc.perform(post("/cars").flashAttr("car", car))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/cars"));
    }

    @Test
    @DisplayName("Update car - success")
    void update_success() throws Exception {
        Car updatedCar = getSavedCar();

        when(carService.update(ID, updatedCar)).thenReturn(updatedCar);

        mockMvc.perform(put("/cars/{id}", ID).flashAttr("car", updatedCar))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/cars"));
    }

    @Test
    @DisplayName("Get car by id - success")
    void getById_success() throws Exception {
        Car car = getSavedCar();

        when(carService.getById(ID)).thenReturn(car);

        mockMvc.perform(get("/cars/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(view().name("car-info"));
    }

    @Test
    @DisplayName("Get all cars - success")
    void getAll_success() throws Exception {
        when(carService.getAll()).thenReturn(List.of(getSavedCar()));

        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(view().name("cars"));
    }

    @Test
    @DisplayName("Delete car by id - success")
    void delete_success() throws Exception {
        mockMvc.perform(delete("/cars/{id}", ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/cars"));
    }

    @Test
    @DisplayName("Get car form - success")
    void getForm_success() throws Exception {
        mockMvc.perform(get("/cars/form"))
                .andExpect(status().isOk())
                .andExpect(view().name("car-form"));
    }

    @Test
    @DisplayName("Get update user form - success")
    void getUpdateForm_success() throws Exception {
        when(carService.getById(ID)).thenReturn(getSavedCar());

        mockMvc.perform(get("/cars/form/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(view().name("update-car-form"));
    }

    private Car getCar() {
        User user = User.builder()
                .email("test@email")
                .build();
        Car car = new Car();
        car.setType(CarType.VAN);
        car.setLicensePlate("abc");
        car.setCreatedAt(LocalDateTime.now());
        car.setUser(user);

        return car;
    }

    private Car getSavedCar() {
        Car savedCar = getCar();
        savedCar.setId(ID);

        return savedCar;
    }
}