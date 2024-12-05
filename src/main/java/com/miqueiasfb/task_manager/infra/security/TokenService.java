package com.miqueiasfb.task_manager.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.miqueiasfb.task_manager.dto.AuthResponseDTO;
import com.miqueiasfb.task_manager.exceptions.TokenException;
import com.miqueiasfb.task_manager.models.User;

@Service
public class TokenService {
  @Value("${api.security.token.secret}")
  private String secret;

  @Value("${api.security.token.expiration}")
  private int tokenExpirationTime;

  @Value("${api.security.token.refresh.expiration}")
  private int refreshTokenExpirationTime;

  public AuthResponseDTO getToken(User user) {
    String token = this.generateToken(user, tokenExpirationTime);
    String refreshToken = this.generateToken(user, refreshTokenExpirationTime);

    return new AuthResponseDTO(token, refreshToken);
  }

  public AuthResponseDTO getRefreshedToken(User user) {
    var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    return this.getToken(user);
  }

  public String generateToken(User user, int expirationTime) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      String token = JWT.create()
          .withIssuer("Task-Manager-API")
          .withSubject(user.getEmail())
          .withExpiresAt(this.generateExpirationDate(expirationTime))
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

  private Instant generateExpirationDate(int expirationTime) {
    return LocalDateTime.now().plusHours(expirationTime).toInstant(ZoneOffset.of("-03:00"));
  }
}
