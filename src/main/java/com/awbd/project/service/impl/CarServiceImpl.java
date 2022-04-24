package com.awbd.project.service.impl;

import com.awbd.project.error.ErrorMessage;
import com.awbd.project.error.exception.ConflictException;
import com.awbd.project.error.exception.ForbiddenActionException;
import com.awbd.project.error.exception.ResourceNotFoundException;
import com.awbd.project.model.Car;
import com.awbd.project.model.security.User;
import com.awbd.project.repository.CarRepository;
import com.awbd.project.service.CarService;
import com.awbd.project.service.UserService;
import com.awbd.project.service.security.JpaUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final JpaUserDetailsService jpaUserDetailsService;
    private final UserService userService;

    @Override
    public Car create(Car car) {
        User user = userService.getByEmail(jpaUserDetailsService.getCurrentUserPrincipal().getUsername());
        checkCarNotExisting(car, user);

        car.setUser(user);
        car.setCreatedAt(LocalDateTime.now());
        return carRepository.save(car);
    }

    @Override
    public Car update(Long id, Car car) {
        Car existingCar = getById(id);
        if (!existingCar.getUser().getEmail().equals(jpaUserDetailsService.getCurrentUserPrincipal().getUsername())) {
            throw new ForbiddenActionException(ErrorMessage.FORBIDDEN);
        }
        if (!existingCar.getLicensePlate().equals(car.getLicensePlate())) {
            checkCarNotExisting(car, car.getUser());
        }

        copyValues(existingCar, car);

        return carRepository.save(existingCar);
    }

    @Override
    public Car getById(Long id) {
        Car car = carRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND, "car", id));
        boolean isAdmin = jpaUserDetailsService.hasAuthority("ROLE_ADMIN");
        if (!isAdmin && !car.getUser().getEmail().equals(jpaUserDetailsService.getCurrentUserPrincipal().getUsername())) {
            throw new ForbiddenActionException(ErrorMessage.FORBIDDEN);
        }

        return car;
    }

    @Override
    public List<Car> getAll() {
        boolean isAdmin = jpaUserDetailsService.hasAuthority("ROLE_ADMIN");
        if (isAdmin) {
            return carRepository.findAll();
        }
        return carRepository.findAllByEmail(jpaUserDetailsService.getCurrentUserPrincipal().getUsername());
    }

    @Override
    public void deleteById(Long id) {
        Car car = getById(id);
        carRepository.delete(car);
    }

    private void checkCarNotExisting(Car car, User user) {
        if (carRepository.existsByLicensePlateAndUser(car.getLicensePlate(), user)) {
            throw new ConflictException(ErrorMessage.ALREADY_EXISTS, "car", "license plate");
        }
    }

    private void copyValues(Car to, Car from) {
        to.setType(from.getType());
        to.setLicensePlate(from.getLicensePlate());
    }
}
