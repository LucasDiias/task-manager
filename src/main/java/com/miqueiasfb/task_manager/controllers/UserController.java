package com.miqueiasfb.task_manager.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miqueiasfb.task_manager.models.User;
import com.miqueiasfb.task_manager.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PutMapping("/me")
  public void updateMe(@Valid @RequestBody User newUser) {
    this.userService.updateMe(newUser);
  }

  @DeleteMapping("/me")
  public void deleteMe() {
    this.userService.deleteMe();
  }

}
