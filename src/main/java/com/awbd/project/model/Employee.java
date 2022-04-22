package com.awbd.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column(name = "first_name")
    @NotNull(message = "First name is mandatory.")
    @Size(min = 1, max = 150, message = "First name must be between 1 and 100 characters long.")
    private String firstName;

    @Column(name = "last_name")
    @NotNull(message = "Last name is mandatory.")
    @Size(min = 1, max = 150, message = "Last name must be between 1 and 100 characters long.")
    private String lastName;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "phone_number")
    @NotNull(message = "Phone number is mandatory.")
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", message = "Phone number is invalid.")
    private String phoneNumber;

    @Column(name = "salary")
    @NotNull(message = "Salary is mandatory.")
    @Positive(message = "Salary must be a positive number.")
    @Max(value = 50000, message = "Salary must be at most 50000.")
    private Double salary;

    @Column(name = "hire_date")
    @NotNull(message = "Hire date is mandatory.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireDate;

    @ManyToMany
    @JoinTable(name = "employees_appointments",
        joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "appointment_id", referencedColumnName = "id"))
    private List<Appointment> appointments;
}
