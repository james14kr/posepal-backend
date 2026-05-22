package com.posepal.posepal.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

  private final Key key;
  private final long expiration;

  public JwtUtil(@Value("${jwt.secret}") String secret,
                 @Value("${jwt.expiration}") long expiration){
    this.key = Keys.hmacShaKeyFor(secret.getBytes());
    this.expiration = expiration;
  }

  public String generateToken(String email){
    return Jwts.builder()
            .subject(email)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(key)
            .compact();
  }

  public String getEmail(String token){
    return Jwts.parser().verifyWith((javax.crypto.SecretKey) key)
            .build().parseSignedClaims(token).getPayload().getSubject();
  }

  public boolean isValid(String token){
    try {
      Jwts.parser().verifyWith((javax.crypto.SecretKey) key)
              .build().parseSignedClaims(token);
      return true;
    }catch (Exception e){
      return false;
    }
  }

}
