package com.miqueiasfb.task_manager.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miqueiasfb.task_manager.dto.UpdateUserRequestDTO;
import com.miqueiasfb.task_manager.exceptions.ResourceNotFoundException;
import com.miqueiasfb.task_manager.models.User;
import com.miqueiasfb.task_manager.repositories.UserRepository;
import com.miqueiasfb.task_manager.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;
  private final UserRepository userRepository;

  public UserController(UserService userService, UserRepository userRepository) {
    this.userService = userService;
    this.userRepository = userRepository;
  }

  @PutMapping("/me")
  public ResponseEntity<UpdateUserRequestDTO> updateMe(@Valid @RequestBody UpdateUserRequestDTO newUser) {
    User user = this.userRepository.findById(newUser.id())
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    user.setName(newUser.name());
    user.setEmail(newUser.email());
    user.setPhone(newUser.phone());
    user.setBirthDate(newUser.birthDate());
    this.userService.updateMe(user);
    return ResponseEntity
        .ok(new UpdateUserRequestDTO(user.getId().toString(), user.getName(), user.getEmail(), user.getPhone(),
            user.getBirthDate()));
  }

  @DeleteMapping("/me")
  public void deleteMe() {
    this.userService.deleteMe();
  }

}
