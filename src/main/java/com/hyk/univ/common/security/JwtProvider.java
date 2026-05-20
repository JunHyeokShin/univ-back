package com.hyk.univ.common.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import jakarta.annotation.PostConstruct;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hyk.univ.user.domain.Role;

@Component
public class JwtProvider {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration-ms}")
  private long expirationMs;

  private SecretKey key;

  @PostConstruct
  public void init() {
    this.key = Keys.hmacShaKeyFor(this.secret.getBytes(StandardCharsets.UTF_8));
  }

  public String createToken(Long userId, Role role) {
    Date now = new Date();
    return Jwts.builder()
        .subject(String.valueOf(userId))
        .claim("role", role.name())
        .issuedAt(now)
        .expiration(new Date(now.getTime() + this.expirationMs))
        .signWith(this.key)
        .compact();
  }

  public AuthUser parse(String token) {
    Claims claims = Jwts.parser()
        .verifyWith(this.key)
        .build()
        .parseSignedClaims(token)
        .getPayload();
    Long userId = Long.parseLong(claims.getSubject());
    Role role = Role.valueOf(claims.get("role", String.class));
    return new AuthUser(userId, role);
  }

}
