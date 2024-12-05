package com.miqueiasfb.task_manager.controllers;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miqueiasfb.task_manager.dto.LoginRequestDTO;
import com.miqueiasfb.task_manager.dto.RefreshTokenRequestDTO;
import com.miqueiasfb.task_manager.dto.RegisterRequestDTO;
import com.miqueiasfb.task_manager.dto.AuthResponseDTO;
import com.miqueiasfb.task_manager.exceptions.BadRequestException;
import com.miqueiasfb.task_manager.exceptions.ResourceNotFoundException;
import com.miqueiasfb.task_manager.infra.security.TokenService;
import com.miqueiasfb.task_manager.models.User;
import com.miqueiasfb.task_manager.repositories.UserRepository;

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
  public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO body) {
    User user = this.userRepository.findByEmail(body.email())
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    if (passwordEncoder.matches(body.password(), user.getPassword())) {
      return ResponseEntity.ok(tokenService.getToken(user));
    }
    return ResponseEntity.badRequest().build();
  }

  @PostMapping("/register")
  public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO body) {
    Optional<User> user = this.userRepository.findByEmail(body.email());
    if (user.isPresent()) {
      throw new BadRequestException("User already exists");
    }
    User newUser = new User();
    newUser.setName(body.name());
    newUser.setEmail(body.email());
    newUser.setPassword(passwordEncoder.encode(body.password()));
    this.userRepository.save(newUser);
    return ResponseEntity.ok(tokenService.getToken(newUser));
  }

  @PostMapping("/refresh")
  public ResponseEntity<AuthResponseDTO> refresh(@Valid @RequestBody RefreshTokenRequestDTO body) {
    String email = tokenService.validateToken(body.refreshToken());
    if (email == null) {
      throw new BadRequestException("Invalid refresh token");
    }
    User user = this.userRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    return ResponseEntity.ok(tokenService.getRefreshedToken(user));
  }
}
