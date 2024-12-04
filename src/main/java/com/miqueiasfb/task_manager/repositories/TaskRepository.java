package com.miqueiasfb.task_manager.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miqueiasfb.task_manager.models.Task;
import com.miqueiasfb.task_manager.models.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
  Page<Task> findAllByUser(User user, Pageable pageable);

  Optional<Task> findByIdAndUser(Long id, User user);
}
