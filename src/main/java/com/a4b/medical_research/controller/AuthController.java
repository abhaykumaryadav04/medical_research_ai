package com.a4b.medical_research.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a4b.medical_research.dto.LoginRequest;
import com.a4b.medical_research.dto.RegisterRequest;
import com.a4b.medical_research.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
@Autowired
private AuthService authService;

@PostMapping("/register")
public ResponseEntity<String> register(@RequestBody RegisterRequest request){
    return ResponseEntity.ok(authService.register(request));
}
@PostMapping("/login")
ResponseEntity<String> login(@RequestBody LoginRequest request){
    return ResponseEntity.ok(authService.login(request));

}


}
