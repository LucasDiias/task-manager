package com.miqueiasfb.task_manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miqueiasfb.task_manager.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
