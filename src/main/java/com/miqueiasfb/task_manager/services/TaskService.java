package com.miqueiasfb.task_manager.services;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.miqueiasfb.task_manager.entities.Task;
import com.miqueiasfb.task_manager.repositories.TaskRepository;

@Service
public class TaskService {
  private TaskRepository taskRepository;

  public TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public List<Task> create(Task task) {
    taskRepository.save(task);
    return list();
  }

  public List<Task> list() {
    Sort sort = Sort.by("priority").descending().and(
        Sort.by("title").ascending());
    return taskRepository.findAll(sort);
  }

  public Task findById(Long id) {
    return taskRepository.findById(id).orElse(null);
  }

  public List<Task> update(Task task) {
    taskRepository.save(task);
    return list();
  }

  public List<Task> delete(Long id) {
    taskRepository.deleteById(id);
    return list();
  }
}
