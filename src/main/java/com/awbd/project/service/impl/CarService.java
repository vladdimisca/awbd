package com.awbd.project.service.impl;

import com.awbd.project.model.Car;
import com.awbd.project.repository.CarRepository;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car create(Car car) {
        if (carRepository.existsByLicensePlate(car.getLicensePlate())) {
            throw new RuntimeException();
        }
        return carRepository.save(car);
    }
}
