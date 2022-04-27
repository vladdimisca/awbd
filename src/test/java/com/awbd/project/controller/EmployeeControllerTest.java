package com.awbd.project.controller;

import com.awbd.project.model.Employee;
import com.awbd.project.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("h2")
class EmployeeControllerTest {

    private static final Long ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeServiceImpl employeeService;

    @Test
    @DisplayName("Create employee - success")
    void create_success() throws Exception {
        Employee employee = getEmployee();
        Employee savedEmployee = getSavedEmployee();

        when(employeeService.create(employee)).thenReturn(savedEmployee);

        mockMvc.perform(post("/employees").flashAttr("employee", employee))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/employees"));
    }

    @Test
    @DisplayName("Update car - success")
    void update_success() throws Exception {
        Employee updatedEmployee = getSavedEmployee();

        when(employeeService.update(ID, updatedEmployee)).thenReturn(updatedEmployee);

        mockMvc.perform(put("/employees/{id}", ID).flashAttr("employee", updatedEmployee))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/employees"));
    }

    @Test
    @DisplayName("Get car by id - success")
    void getById_success() throws Exception {
        Employee employee = getSavedEmployee();

        when(employeeService.getById(ID)).thenReturn(employee);

        mockMvc.perform(get("/employees/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(view().name("employee-info"));
    }

    @Test
    @DisplayName("Get all employees - success")
    void getAll_success() throws Exception {
        when(employeeService.getAll()).thenReturn(List.of(getSavedEmployee()));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(view().name("employees"));
    }

    @Test
    @DisplayName("Delete employee by id - success")
    void delete_success() throws Exception {
        mockMvc.perform(delete("/employees/{id}", ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/employees"));
    }

    @Test
    @DisplayName("Get employee form - success")
    void getForm_success() throws Exception {
        mockMvc.perform(get("/employees/form"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee-form"));
    }

    @Test
    @DisplayName("Get update employee form - success")
    void getUpdateForm_success() throws Exception {
        when(employeeService.getById(ID)).thenReturn(getSavedEmployee());

        mockMvc.perform(get("/employees/form/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(view().name("update-employee-form"));
    }

    private Employee getEmployee() {
        Employee employee = new Employee();
        employee.setEmail("test@gmail.com");
        employee.setPhoneNumber("0751111222");
        employee.setFirstName("abc");
        employee.setLastName("bcd");
        employee.setSalary(5000.0);
        employee.setHireDate(LocalDate.now());

        return employee;
    }

    private Employee getSavedEmployee() {
        Employee savedEmployee = getEmployee();
        savedEmployee.setId(ID);

        return savedEmployee;
    }
}