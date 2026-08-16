package com.a4b.medical_research.model;

import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.a4b.medical_research.enummeration.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@Table(name = "users")
public class User implements UserDetails {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;
@Column(nullable = false,unique = true)
private String email;
private String password;
@Enumerated(EnumType.STRING)
private Role role;
@Override
public  Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + getRole()));
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