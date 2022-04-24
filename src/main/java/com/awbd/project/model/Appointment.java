package com.awbd.project.model;

import com.awbd.project.model.security.User;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;

    @ManyToOne
    @JoinColumn(name = "fk_user", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "fk_car", referencedColumnName = "id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "fk_job", referencedColumnName = "id")
    private Job job;

    @ManyToMany(mappedBy = "appointments")
    private List<Employee> employees;
}
