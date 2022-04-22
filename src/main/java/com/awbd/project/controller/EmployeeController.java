package com.awbd.project.controller;

import com.awbd.project.model.Employee;
import com.awbd.project.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "employee-form";
        }

        employeeService.create(employee);
        return "redirect:/employees";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update-employee-form";
        }

        employeeService.update(id, employee);
        return "redirect:/employees";
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("employees", employeeService.getAll());
        return "employees";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("employee", employeeService.getById(id));
        return "employee-info";
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        employeeService.deleteById(id);
        return "redirect:/employees";
    }

    @GetMapping("/form")
    public String employeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee-form";
    }

    @GetMapping("/form/{id}")
    public String updateEmployeeForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("employee", employeeService.getById(id));
        return "update-employee-form";
    }
}
