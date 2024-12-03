package com.miqueiasfb.task_manager.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miqueiasfb.task_manager.entities.Task;
import com.miqueiasfb.task_manager.services.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
  private TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @PostMapping
  List<Task> create(@RequestBody Task task) {
    return taskService.create(task);
  }

  @GetMapping
  List<Task> list() {
    return taskService.list();
  }

  @GetMapping("/{id}")
  Task findById(@PathVariable Long id) {
    return taskService.findById(id);
  }

  @PutMapping
  List<Task> update(@RequestBody Task task) {
    return taskService.update(task);
  }

  @DeleteMapping("/{id}")
  List<Task> delete(@PathVariable Long id) {
    return taskService.delete(id);
  }
}
