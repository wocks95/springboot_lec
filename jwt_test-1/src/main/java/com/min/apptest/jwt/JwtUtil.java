package com.min.apptest.jwt;


import java.util.Date;

import javax.crypto.SecretKey;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

// 토큰을 발급해주는 곳

@Component
public class JwtUtil {

  private SecretKey secretKey;
  
  
  
  public JwtUtil(@Value("${spring.jwt.secret}")String secret) {
    
    byte[] keyBytes = Decoders.BASE64.decode(secret); 
    this.secretKey = Keys.hmacShaKeyFor(keyBytes); 
  }
  
  
  public String getUsername(String token) {

    return Jwts.parser()
                        .verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
                        .get("userEmail", String.class);
  }
  
  public String getRole(String token) {
  
      return Jwts.parser()
                          .verifyWith(secretKey)
                          .build()
                          .parseSignedClaims(token)
                          .getPayload()
                          .get("role", String.class);
  }
  
  public Boolean isExpired(String token) {
  
      return Jwts.parser()
                          .verifyWith(secretKey)
                          .build()
                          .parseSignedClaims(token)
                          .getPayload()
                          .getExpiration()
                          .before(new Date());
  }
  
  // 토큰을 생성해서 응답을 해주는 메소드
  public String createJwt(String userEmail, String role, Long expiredMs) {
  
      return Jwts.builder()
              .claim("userEmail", userEmail)
              .claim("role", role)
              .issuedAt(new Date(System.currentTimeMillis())) // 토큰 발행 시간
              .expiration(new Date(System.currentTimeMillis() + expiredMs)) // 토큰 소멸 시간 
              .signWith(secretKey) // 암호화를 진행
              .compact();
  }
  
 
}
