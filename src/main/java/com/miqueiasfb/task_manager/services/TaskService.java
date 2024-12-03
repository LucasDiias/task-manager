package com.miqueiasfb.task_manager.services;

// import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
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

  public Task create(Task task) {
    return taskRepository.save(task);
  }

  public Page<Task> list(int page, int size) {
    Pageable pageable = PageRequest.of(page, size,
        Sort.by(Sort.Order.desc("priority")).and(Sort.by(Sort.Order.asc("title"))));
    return taskRepository.findAll(pageable);
  }

  public Task findById(Long id) {
    return taskRepository.findById(id).orElse(null);
  }

  public Task update(Task task) {
    return taskRepository.save(task);
  }

  public void delete(Long id) {
    taskRepository.deleteById(id);
  }
}
