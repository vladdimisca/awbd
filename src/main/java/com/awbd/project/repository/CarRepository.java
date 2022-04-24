package com.awbd.project.repository;

import com.awbd.project.model.Car;
import com.awbd.project.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    boolean existsByLicensePlateAndUser(String licensePlate, User user);

    @Query("SELECT c from Car c WHERE c.user.email = :email")
    List<Car> findAllByEmail(@Param("email") String email);
}
