package com.nexusai.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nexusai.backend.entity.Role;
import com.nexusai.backend.entity.User;
import com.nexusai.backend.exception.EmailAlreadyExistsException;
import com.nexusai.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        user.setRole(Role.EMPLOYEE);

        return userRepository.save(user);
    }
}
