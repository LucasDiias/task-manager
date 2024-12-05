package com.miqueiasfb.task_manager.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.miqueiasfb.task_manager.exceptions.ResourceNotFoundException;
import com.miqueiasfb.task_manager.models.User;
import com.miqueiasfb.task_manager.repositories.UserRepository;

@Service
public class UserService {
  UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void updateMe(User newUser) {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof User) {
      User user = (User) principal;
      user.setName(newUser.getName());
      user.setEmail(newUser.getEmail());
      user.setPassword(newUser.getPassword());
      userRepository.save(user);
    } else if (principal instanceof String) {
      User user = userRepository.findByEmail((String) principal)
          .orElseThrow(() -> new ResourceNotFoundException("User not found"));
      user.setEmail(newUser.getEmail());
      user.setPassword(newUser.getPassword());
      userRepository.save(user);
    } else {
      throw new ResourceNotFoundException("User not found");
    }
  }

  public void deleteMe() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof User) {
      userRepository.delete((User) principal);
    } else if (principal instanceof String) {
      User user = userRepository.findByEmail((String) principal)
          .orElseThrow(() -> new ResourceNotFoundException("User not found"));
      userRepository.delete(user);
    } else {
      throw new ResourceNotFoundException("User not found");
    }
  }

}
