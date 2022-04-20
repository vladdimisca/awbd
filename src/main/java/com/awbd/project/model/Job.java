package com.awbd.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column(name = "type")
    @NotNull(message = "Type must not be null.")
    @Enumerated(EnumType.STRING)
    private JobType type;

    @Column(name = "price")
    @NotNull(message = "Price must not be null.")
    @Positive(message = "Price must be a positive number.")
    @Max(value = 1000, message = "Price must be at most 1000.")
    private Double price;

    @Column(name = "duration_minutes")
    @NotNull(message = "Duration minutes must not be null.")
    @Positive(message = "Duration minutes must be a positive number.")
    @Max(value = 90, message = "Duration minutes must be at most 90.")
    private Long durationMinutes;

    @Column(name = "car_type")
    @NotNull(message = "Car type must not be null.")
    @Enumerated(EnumType.STRING)
    private CarType carType;

    @Column(name = "number_of_employees")
    @NotNull(message = "Number of employees must not be null.")
    @Positive(message = "Number of employees must be a positive number.")
    @Max(value = 5, message = "Number of employees must be at most 5.")
    private Integer numberOfEmployees;

    @OneToMany(mappedBy = "job", fetch = FetchType.LAZY)
    private List<Appointment> appointments;
}
