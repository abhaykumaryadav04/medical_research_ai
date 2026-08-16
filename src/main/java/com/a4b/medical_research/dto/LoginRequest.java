package com.a4b.medical_research.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
private String email;
private String password;
}
