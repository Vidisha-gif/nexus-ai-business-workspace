package com.nexusai.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.nexusai.backend.entity.Role;

@Data
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;
    private Role role;
}