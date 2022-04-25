package com.awbd.project.repository.security;

import com.awbd.project.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END " +
            "FROM User u WHERE u.email = :email AND u.userDetails.phoneNumber = :phoneNumber")
    Boolean existsByEmailOrPhoneNumber(@Param("email") String email, @Param("phoneNumber") String phoneNumber);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END " +
            "FROM User u WHERE u.email = :email")
    Boolean existsByEmail(@Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END " +
            "FROM User u WHERE u.userDetails.phoneNumber = :phoneNumber")
    Boolean existsByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
