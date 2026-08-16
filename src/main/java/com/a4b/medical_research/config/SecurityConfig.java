package com.a4b.medical_research.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.a4b.medical_research.security.CustumUserDetailsService;
import com.a4b.medical_research.security.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CustumUserDetailsService custumUserDetailsService;
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        http.csrf(csrf-> csrf.disable())
            .authorizeHttpRequests(request-> request.requestMatchers("/api/auth/login","/api/auth/register")
             .permitAll().anyRequest().authenticated())
             .sessionManagement(sesson->sesson.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
             .authenticationProvider(getAuthenticationProvider())
             .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
             return http.build();
    }
    @Bean
    public  AuthenticationProvider getAuthenticationProvider() {
       DaoAuthenticationProvider provider=new DaoAuthenticationProvider(custumUserDetailsService);
      provider.setPasswordEncoder(getEncoder());
      return provider;
      
    }
    @Bean
  public PasswordEncoder getEncoder(){
    return new BCryptPasswordEncoder();
}
@Bean
public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration config){
    return config.getAuthenticationManager();
}
}
