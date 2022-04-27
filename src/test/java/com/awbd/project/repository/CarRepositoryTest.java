package com.awbd.project.repository;

import com.awbd.project.model.Car;
import com.awbd.project.repository.security.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("h2")
@Rollback(false)
@Slf4j
class CarRepositoryTest {

    private static final String USER_EMAIL = "random@gmail.com";

    @Autowired
    private CarRepository carRepository;

    @Test
    @DisplayName("Find all cars by email")
    public void findAllByEmail() {
        List<Car> cars = carRepository.findAllByEmail(USER_EMAIL);

        assertEquals(2, cars.size());

        cars.forEach(car -> log.info("Car id={} and license plate={}", car.getId(), car.getLicensePlate()));
    }

}