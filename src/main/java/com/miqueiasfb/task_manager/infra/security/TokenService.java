package com.miqueiasfb.task_manager.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.miqueiasfb.task_manager.entities.User;
import com.miqueiasfb.task_manager.exceptions.TokenException;

@Service
public class TokenService {
  @Value("${api.security.token.secret}")
  private String secret;

  public String generateToken(User user) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      String token = JWT.create()
          .withIssuer("Task-Manager-API")
          .withSubject(user.getEmail())
          .withExpiresAt(this.generateExpirationDate())
          .sign(algorithm);
      return token;
    } catch (JWTCreationException e) {
      throw new TokenException("Error creating token", e);
    }
  }

  public String validateToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.require(algorithm)
          .withIssuer("Task-Manager-API")
          .build()
          .verify(token)
          .getSubject();
    } catch (JWTVerificationException e) {
      return null;
    }
  }

  private Instant generateExpirationDate() {
    return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-03:00"));
  }
}
