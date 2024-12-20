package com.miqueiasfb.task_manager.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.miqueiasfb.task_manager.dto.TaskResponseDTO;
import com.miqueiasfb.task_manager.exceptions.ResourceNotFoundException;
import com.miqueiasfb.task_manager.exceptions.UnauthorizedException;
import com.miqueiasfb.task_manager.models.Task;
import com.miqueiasfb.task_manager.models.User;
import com.miqueiasfb.task_manager.repositories.TaskRepository;
import com.miqueiasfb.task_manager.repositories.UserRepository;

@Service
public class TaskService {
  private TaskRepository taskRepository;
  private UserRepository userRepository;

  public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
    this.taskRepository = taskRepository;
    this.userRepository = userRepository;
  }

  public TaskResponseDTO create(Task task) {
    User user = getCurrentUser();
    task.setUser(user);
    taskRepository.save(task);
    return new TaskResponseDTO(
        task.getId(),
        task.getTitle(),
        task.getDescription(),
        task.getPriority(),
        task.isDone(),
        task.getDoneAt(),
        task.getCreatedAt());
  }

  public Page<TaskResponseDTO> list(int page, int size) {
    User user = getCurrentUser();
    Pageable pageable = PageRequest.of(page, size,
        Sort.by(Sort.Order.desc("priority")).and(Sort.by(Sort.Order.asc("title"))));
    Page<Task> tasks = taskRepository.findAllByUser(user, pageable);
    return tasks.map(task -> new TaskResponseDTO(
        task.getId(),
        task.getTitle(),
        task.getDescription(),
        task.getPriority(),
        task.isDone(),
        task.getDoneAt(),
        task.getCreatedAt()));
  }

  public TaskResponseDTO findById(Long id) {
    User user = getCurrentUser();
    Task task = taskRepository.findByIdAndUser(id, user).orElse(null);
    if (task == null) {
      throw new ResourceNotFoundException("Task not found");
    }
    return new TaskResponseDTO(
        task.getId(),
        task.getTitle(),
        task.getDescription(),
        task.getPriority(),
        task.isDone(),
        task.getDoneAt(),
        task.getCreatedAt());
  }

  public TaskResponseDTO update(Task task) {
    User user = getCurrentUser();
    task.setUser(user);
    taskRepository.save(task);
    return new TaskResponseDTO(
        task.getId(),
        task.getTitle(),
        task.getDescription(),
        task.getPriority(),
        task.isDone(),
        task.getDoneAt(),
        task.getCreatedAt());
  }

  public void delete(Long id) {
    User user = getCurrentUser();
    Task task = taskRepository.findByIdAndUser(id, user)
        .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    taskRepository.delete(task);
  }

  private User getCurrentUser() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String email;

    if (principal instanceof User) {
      email = ((User) principal).getEmail();
    } else if (principal instanceof String) {
      email = (String) principal;
    } else {
      throw new UnauthorizedException("Unauthorized");
    }

    return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
  }
}
