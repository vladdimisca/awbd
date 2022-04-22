package com.awbd.project.bootstrap;

import com.awbd.project.model.SexType;
import com.awbd.project.model.UserDetails;
import com.awbd.project.model.security.Authority;
import com.awbd.project.model.security.User;
import com.awbd.project.repository.security.AuthorityRepository;
import com.awbd.project.repository.security.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private AuthorityRepository authorityRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        loadUserData();
    }

    private void loadUserData() {
        if (userRepository.count() == 0){
            Authority adminRole = authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build());
            Authority guestRole = authorityRepository.save(Authority.builder().role("ROLE_GUEST").build());

            UserDetails adminDetails = UserDetails.builder()
                    .firstName("admin")
                    .lastName("awbd")
                    .phoneNumber("0761111111111")
                    .sex(SexType.MALE)
                    .build();

            User admin = User.builder()
                    .email("admin@awbd.com")
                    .password(passwordEncoder.encode("12345"))
                    .authority(adminRole)
                    .userDetails(adminDetails)
                    .build();

            UserDetails guestDetails = UserDetails.builder()
                    .firstName("guest")
                    .lastName("awbd")
                    .phoneNumber("0751111111111")
                    .sex(SexType.MALE)
                    .build();

            User guest = User.builder()
                    .email("guest@awbd.com")
                    .password(passwordEncoder.encode("12345"))
                    .authority(guestRole)
                    .userDetails(guestDetails)
                    .build();

            userRepository.save(admin);
            userRepository.save(guest);
        }
    }
}
