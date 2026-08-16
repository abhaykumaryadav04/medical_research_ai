package com.a4b.medical_research.security;

import java.security.Key;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.a4b.medical_research.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
   @Value("${jwt.secret-key}")
    private String secretKey;
  
 
@Value("${jwt.expiration}")
private long expiration;

    public String generateToken(User user){
        return Jwts.builder()
         .claims(Map.of(
                    "userId", user.getId(),
                    "role", user.getRole().name()
            ))
                   .subject(user.getEmail())
                   .issuedAt(new Date(System.currentTimeMillis()))
                   .expiration(new Date(System.currentTimeMillis()+expiration))
                   .signWith(getKey())
                   .compact();
                   
    }

    private Key getKey() {
        byte[] keyBytes=Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
      
    }

    public String extractUseremail(String token) {
       return extractClaims(token, Claims::getSubject);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
      final String useremail=extractUseremail(token);
      return (useremail.equals(userDetails.getUsername())&&!isTokenExpired(token));
    }
    public <T>T extractClaims(String token,Function<Claims,T> claimResolver){
        Claims claims=extractAllClaims(token);
   return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
     return Jwts.parser()
            .verifyWith((SecretKey) getKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
    private boolean isTokenExpired(String token){
    return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
     return extractClaims(token,Claims::getExpiration );
    }
}
