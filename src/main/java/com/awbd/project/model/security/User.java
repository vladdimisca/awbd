package com.awbd.project.model.security;

import com.awbd.project.model.Appointment;
import com.awbd.project.model.Car;
import com.awbd.project.model.UserDetails;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "email")
    @NotEmpty(message="Email cannot be empty.")
    @Email(message = "Email has an invalid format.")
    private String email;

    @Column(name = "password")
    @NotNull(message = "Password is mandatory.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z]).{6,}",
            message = "Password must contain at least 6 characters including one digit and one lower case letter.")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Car> cars;

    @Valid
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_user_details", referencedColumnName = "id")
    private UserDetails userDetails;

    @Singular
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private Set<Authority> authorities;

    @Builder.Default
    private Boolean enabled = true;

    @Builder.Default
    private Boolean accountNotExpired = true;

    @Builder.Default
    private Boolean accountNotLocked = true;

    @Builder.Default
    private Boolean credentialsNotExpired = true;
}
