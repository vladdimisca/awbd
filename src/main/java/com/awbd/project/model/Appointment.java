package com.awbd.project.model;

import com.awbd.project.model.security.User;
import com.awbd.project.model.validator.AppointmentTimeConstraint;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time")
    @AppointmentTimeConstraint
    @NotNull(message = "Appointment time is mandatory.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_user", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull(message = "Car is mandatory.")
    @JoinColumn(name = "fk_car", referencedColumnName = "id")
    private Car car;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull(message = "Job is mandatory.")
    @JoinColumn(name = "fk_job", referencedColumnName = "id")
    private Job job;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "employees_appointments",
            joinColumns = @JoinColumn(name = "appointment_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"))
    private List<Employee> employees;
}
