package com.awbd.project.repository.security;

import com.awbd.project.model.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByRole(String role);
}
