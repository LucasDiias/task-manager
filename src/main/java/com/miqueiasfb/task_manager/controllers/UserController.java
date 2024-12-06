package com.miqueiasfb.task_manager.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miqueiasfb.task_manager.dto.UpdateUserRequestDTO;
import com.miqueiasfb.task_manager.dto.UserResponseDTO;
import com.miqueiasfb.task_manager.exceptions.ResourceNotFoundException;
import com.miqueiasfb.task_manager.infra.security.TokenService;
import com.miqueiasfb.task_manager.models.User;
import com.miqueiasfb.task_manager.repositories.UserRepository;
import com.miqueiasfb.task_manager.services.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;
  private final UserRepository userRepository;
  private final TokenService tokenService;

  public UserController(UserService userService, UserRepository userRepository, TokenService tokenService) {
    this.userService = userService;
    this.userRepository = userRepository;
    this.tokenService = tokenService;
  }

  @GetMapping("/me")
  public ResponseEntity<UserResponseDTO> getMe(HttpServletRequest request) {
    String token = this.getCookieValue(request, "token");
    String userId = this.tokenService.validateToken(token);
    if (userId != null) {
      User user = this.userRepository.findById(userId)
          .orElseThrow(() -> new ResourceNotFoundException("User not found"));
      return ResponseEntity
          .ok(new UserResponseDTO(user.getId().toString(), user.getName(), user.getEmail(), user.getPhone(),
              user.getBirthDate()));
    }
    return null;
  }

  @PutMapping("/me")
  public ResponseEntity<UserResponseDTO> updateMe(@Valid @RequestBody UpdateUserRequestDTO newUser) {
    User user = this.userRepository.findById(newUser.id())
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    user.setName(newUser.name());
    user.setEmail(newUser.email());
    user.setPhone(newUser.phone());
    user.setBirthDate(newUser.birthDate());
    this.userService.updateMe(user);
    return ResponseEntity
        .ok(new UserResponseDTO(user.getId().toString(), user.getName(), user.getEmail(), user.getPhone(),
            user.getBirthDate()));
  }

  @DeleteMapping("/me")
  public void deleteMe() {
    this.userService.deleteMe();
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
