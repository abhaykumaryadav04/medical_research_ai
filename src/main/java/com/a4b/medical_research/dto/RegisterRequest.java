package com.a4b.medical_research.dto;

import com.a4b.medical_research.enummeration.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {
private String username;
private String email;
private String password;
@Enumerated(EnumType.STRING)
private Role role;

}
