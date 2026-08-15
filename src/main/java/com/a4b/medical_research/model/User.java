package com.a4b.medical_research.model;

import java.util.Collection;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;

@Entity
@Data
@Builder
public class User implements UserDetails {
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;
private String email;
private String password;
@Override
public private Collection<? extends GrantedAuthority> getAuthorities() {
   
}

@Override
public @Nullable String getPassword() {
return password;
}

@Override
public String getUsername() {
   return email;
}
}