package com.awbd.project.controller;

import com.awbd.project.model.Appointment;
import com.awbd.project.service.AppointmentService;
import com.awbd.project.service.CarService;
import com.awbd.project.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final CarService carService;
    private final JobService jobService;

    @PostMapping
    public String create(@Valid @ModelAttribute Appointment appointment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "appointment-form";
        }

        appointmentService.create(appointment);
        return "redirect:/appointments";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute Appointment appointment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update-appointment-form";
        }

        appointmentService.update(id, appointment);
        return "redirect:/appointments";
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("appointments", appointmentService.getAll());
        return "appointments";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("appointment", appointmentService.getById(id));
        return "appointment-info";
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        appointmentService.deleteById(id);
        return "redirect:/appointments";
    }

    @GetMapping("/form")
    public String appointmentForm(Model model) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("cars", carService.getAll());
        model.addAttribute("jobs", jobService.getAll());
        return "appointment-form";
    }

    @GetMapping("/form/{id}")
    public String updateAppointmentForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("appointment", appointmentService.getById(id));
        return "update-appointment-form";
    }
}