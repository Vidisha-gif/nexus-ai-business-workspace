package com.nexusai.backend.config;

import com.nexusai.backend.entity.Role;
import com.nexusai.backend.entity.User;
import com.nexusai.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (userRepository.findByEmail("admin@nexusai.com").isPresent()) {
            return;
        }

        User admin = User.builder()
                .firstName("Admin")
                .lastName("Nexus")
                .email("admin@nexusai.com")
                .password(passwordEncoder.encode("admin123"))
                .role(Role.ADMIN)
                .build();

        userRepository.save(admin);

        System.out.println("=========================================");
        System.out.println(" Default Admin Created Successfully");
        System.out.println(" Email    : admin@nexusai.com");
        System.out.println(" Password : admin123");
        System.out.println("=========================================");
    }
}