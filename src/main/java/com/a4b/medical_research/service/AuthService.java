package com.a4b.medical_research.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.a4b.medical_research.dto.LoginRequest;
import com.a4b.medical_research.dto.RegisterRequest;
import com.a4b.medical_research.model.User;
import com.a4b.medical_research.repo.UserRepo;

@Service
public class AuthService {
@Autowired
private PasswordEncoder passwordEncoder;
@Autowired
private UserRepo userRepo;
@Autowired
private AuthenticationManager authenticationManager;
    public String  register(RegisterRequest request) {
      if(userRepo.existsByEmail(request.getEmail())){
        throw new RuntimeException("User alreasy register");
      }
      User user=User.builder().email(request.getEmail())
                              .name(request.getUsername())
                              .password(passwordEncoder.encode(request.getPassword()))
                              .role(request.getRole())
                              .build();
        userRepo.save(user);
        return"Successfully Register";
    }

    public String login(LoginRequest request) {
       Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
       if(authentication.isAuthenticated()){
        return "Success";
       }
       return "Login failed";
    }

}
