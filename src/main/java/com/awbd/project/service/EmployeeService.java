package com.awbd.project.service;

import com.awbd.project.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee create(Employee employee);

    Employee update(Long id, Employee employee);

    Employee getById(Long id);

    List<Employee> getAll();

    void deleteById(Long id);
}
