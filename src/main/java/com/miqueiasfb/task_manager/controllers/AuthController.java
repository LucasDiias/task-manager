package com.miqueiasfb.task_manager.controllers;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miqueiasfb.task_manager.dto.LoginRequestDTO;
import com.miqueiasfb.task_manager.dto.RegisterRequestDTO;
import com.miqueiasfb.task_manager.dto.AuthResponseDTO;
import com.miqueiasfb.task_manager.exceptions.BadRequestException;
import com.miqueiasfb.task_manager.exceptions.ResourceNotFoundException;
import com.miqueiasfb.task_manager.infra.security.TokenService;
import com.miqueiasfb.task_manager.models.User;
import com.miqueiasfb.task_manager.repositories.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenService tokenService;

  @PostMapping("/login")
  public ResponseEntity<Void> login(@Valid @RequestBody LoginRequestDTO body, HttpServletResponse response) {
    User user = this.userRepository.findByEmail(body.email())
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    if (passwordEncoder.matches(body.password(), user.getPassword())) {
      AuthResponseDTO tokens = tokenService.getToken(user);
      addTokensToCookies(response, tokens);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.badRequest().build();
  }

  @PostMapping("/register")
  public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequestDTO body, HttpServletResponse response) {
    Optional<User> user = this.userRepository.findByEmail(body.email());
    if (user.isPresent()) {
      throw new BadRequestException("User already exists");
    }
    User newUser = new User();
    newUser.setName(body.name());
    newUser.setEmail(body.email());
    newUser.setPassword(passwordEncoder.encode(body.password()));
    this.userRepository.save(newUser);
    AuthResponseDTO tokens = tokenService.getToken(newUser);
    addTokensToCookies(response, tokens);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/refresh")
  public ResponseEntity<Void> refresh(HttpServletRequest request, HttpServletResponse response) {
    String refreshToken = getCookieValue(request, "refreshToken");
    if (refreshToken == null) {
      throw new BadRequestException("Refresh token not found");
    }
    String id = this.tokenService.validateToken(refreshToken);
    User user = this.userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    AuthResponseDTO tokens = tokenService.getRefreshedToken(user);
    addTokensToCookies(response, tokens);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(HttpServletResponse response) {
    removeTokensFromCookies(response);
    return ResponseEntity.ok().build();
  }

  private void addTokensToCookies(HttpServletResponse response, AuthResponseDTO tokens) {
    Cookie tokenCookie = new Cookie("token", tokens.token());
    tokenCookie.setHttpOnly(true);
    tokenCookie.setPath("/");
    tokenCookie.setMaxAge(60 * 60); // 1h

    Cookie refreshTokenCookie = new Cookie("refreshToken", tokens.refreshToken());
    refreshTokenCookie.setHttpOnly(true);
    refreshTokenCookie.setPath("/");
    refreshTokenCookie.setMaxAge(60 * 60 * 24 * 7); // 7d

    response.addCookie(tokenCookie);
    response.addCookie(refreshTokenCookie);
  }

  private void removeTokensFromCookies(HttpServletResponse response) {
    Cookie tokenCookie = new Cookie("token", null);
    tokenCookie.setHttpOnly(true);
    tokenCookie.setPath("/");
    tokenCookie.setMaxAge(0); // Remove cookie

    Cookie refreshTokenCookie = new Cookie("refreshToken", null);
    refreshTokenCookie.setHttpOnly(true);
    refreshTokenCookie.setPath("/");
    refreshTokenCookie.setMaxAge(0); // Remove cookie

    response.addCookie(tokenCookie);
    response.addCookie(refreshTokenCookie);
  }

  private String getCookieValue(HttpServletRequest request, String cookieName) {
    if (request.getCookies() != null) {
      for (Cookie cookie : request.getCookies()) {
        if (cookie.getName().equals(cookieName)) {
          return cookie.getValue();
        }
      }
    }
    return null;
  }
}
