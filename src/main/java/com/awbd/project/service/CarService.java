package com.awbd.project.service;

import com.awbd.project.model.Car;

import java.util.List;

public interface CarService {
    Car create(Car car);

    Car update(Long id, Car car);

    Car getById(Long id);

    List<Car> getAll();

    void deleteById(Long id);
}
