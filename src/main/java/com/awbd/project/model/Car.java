package com.awbd.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column(name = "type")
    @NotNull(message = "Type is mandatory.")
    @Enumerated(EnumType.STRING)
    private CarType type;

    @Column(name = "license_plate")
    @NotNull(message = "License plate is mandatory.")
    @Size(min = 1, max = 100, message = "License plate must be between 1 and 100 characters long.")
    private String licensePlate;

    @Column(name = "created_at")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY)
    private List<Appointment> appointments;
}
