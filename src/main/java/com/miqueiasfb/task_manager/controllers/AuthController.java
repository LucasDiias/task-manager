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
import com.miqueiasfb.task_manager.dto.ResponseDTO;
import com.miqueiasfb.task_manager.entities.User;
import com.miqueiasfb.task_manager.infra.security.TokenService;
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
  public ResponseEntity<ResponseDTO> login(@Valid @RequestBody LoginRequestDTO body) {
    User user = this.userRepository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
    if (passwordEncoder.matches(body.password(), user.getPassword())) {
      String token = this.tokenService.generateToken(user);
      return ResponseEntity.ok(new ResponseDTO(token));
    }
    return ResponseEntity.badRequest().build();
  }

  @PostMapping("/register")
  public ResponseEntity<ResponseDTO> register(@Valid @RequestBody RegisterRequestDTO body) {
    Optional<User> user = this.userRepository.findByEmail(body.email());
    if (user.isEmpty()) {
      User newUser = new User();
      newUser.setEmail(body.email());
      newUser.setPassword(passwordEncoder.encode(body.password()));
      this.userRepository.save(newUser);

      String token = this.tokenService.generateToken(newUser);
      return ResponseEntity.ok(new ResponseDTO(token));
    }
    return ResponseEntity.badRequest().build();
  }
}
