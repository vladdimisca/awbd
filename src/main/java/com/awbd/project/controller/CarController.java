package com.awbd.project.controller;

import com.awbd.project.model.Car;
import com.awbd.project.model.Job;
import com.awbd.project.service.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Car car, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "car-form";
        }

        carService.create(car);
        return "redirect:/cars";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute Car car, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update-car-form";
        }

        carService.update(id, car);
        return "redirect:/cars";
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("cars", carService.getAll());
        return "cars";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("car", carService.getById(id));
        return "car-info";
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        carService.deleteById(id);
        return "redirect:/cars";
    }

    @GetMapping("/form")
    public String carForm(Model model) {
        model.addAttribute("car", new Car());
        return "car-form";
    }

    @GetMapping("/form/{id}")
    public String updateCarForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("car", carService.getById(id));
        return "update-car-form";
    }
}
