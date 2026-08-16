package com.a4b.medical_research.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    private CustumUserDetailsService custumUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
System.out.println("JWT FILTER: " + request.getRequestURI());
System.out.println("AUTH HEADER: " + request.getHeader("Authorization"));
    String authHeader=request.getHeader("Authorization");
    String token=null;
    String useremail=null;
    if(authHeader!=null&&authHeader.startsWith("Bearer ")){
        token=authHeader.substring(7);
        useremail=jwtService.extractUseremail(token);
    }
    if(useremail!=null&&SecurityContextHolder.getContext().getAuthentication()==null){
        UserDetails userDetails=custumUserDetailsService.loadUserByUsername(useremail);
        if(jwtService.validateToken(token,userDetails)){
     

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
    }
        }
        filterChain.doFilter(request, response);
    }

       
    }
    


