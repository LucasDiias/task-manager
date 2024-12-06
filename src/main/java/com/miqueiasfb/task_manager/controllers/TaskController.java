package com.miqueiasfb.task_manager.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miqueiasfb.task_manager.dto.TaskResponseDTO;
import com.miqueiasfb.task_manager.models.Task;
import com.miqueiasfb.task_manager.services.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
  private TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @PostMapping
  public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody Task task) {
    TaskResponseDTO createdTask = taskService.create(task);
    return ResponseEntity.ok(createdTask);
  }

  @GetMapping
  public ResponseEntity<Page<TaskResponseDTO>> listTasks(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Page<TaskResponseDTO> tasks = taskService.list(page, size);
    return ResponseEntity.ok(tasks);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
    TaskResponseDTO task = taskService.findById(id);
    return ResponseEntity.ok(task);
  }

  @PutMapping
  public ResponseEntity<TaskResponseDTO> update(@Valid @RequestBody Task task) {
    TaskResponseDTO updatedTask = taskService.update(task);
    return ResponseEntity.ok(updatedTask);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    taskService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
