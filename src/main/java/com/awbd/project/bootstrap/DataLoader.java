package com.awbd.project.bootstrap;

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

            User admin = User.builder()
                    .email("admin@awbd.com")
                    .password(passwordEncoder.encode("12345"))
                    .authority(adminRole)
                    .build();

            User guest = User.builder()
                    .email("guest@awbd.com")
                    .password(passwordEncoder.encode("12345"))
                    .authority(guestRole)
                    .build();

            userRepository.save(admin);
            userRepository.save(guest);
        }
    }
}
