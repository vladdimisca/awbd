package com.awbd.project.service.impl;

import com.awbd.project.error.ErrorMessage;
import com.awbd.project.error.exception.ConflictException;
import com.awbd.project.error.exception.ResourceNotFoundException;
import com.awbd.project.model.Car;
import com.awbd.project.repository.CarRepository;
import com.awbd.project.service.CarService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car create(Car car) {
        checkCarNotExisting(car);
        car.setCreatedAt(LocalDateTime.now());
        return carRepository.save(car);
    }

    @Override
    public Car update(Long id, Car car) {
        Car existingCar = getById(id);
        if (!existingCar.getLicensePlate().equals(car.getLicensePlate())) {
            checkCarNotExisting(car);
        }

        copyValues(existingCar, car);

        return carRepository.save(existingCar);
    }

    @Override
    public Car getById(Long id) {
        return carRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND, "car", id));
    }

    @Override
    public List<Car> getAll() {
        return carRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        Car car = getById(id);
        carRepository.delete(car);
    }

    private void checkCarNotExisting(Car car) {
        if (carRepository.existsByLicensePlate(car.getLicensePlate())) {
            throw new ConflictException(ErrorMessage.ALREADY_EXISTS, "car", "license plate");
        }
    }

    private void copyValues(Car to, Car from) {
        to.setType(from.getType());
        to.setLicensePlate(from.getLicensePlate());
    }
}
