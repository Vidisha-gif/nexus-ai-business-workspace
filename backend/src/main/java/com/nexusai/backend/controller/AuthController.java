package com.nexusai.backend.controller;

import com.nexusai.backend.dto.AuthResponse;
import com.nexusai.backend.dto.RegisterRequest;
import com.nexusai.backend.entity.User;
import com.nexusai.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        User savedUser = authService.register(user);

        AuthResponse response = new AuthResponse(
                "User registered successfully",
                savedUser.getEmail(),
                savedUser.getRole().name()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
