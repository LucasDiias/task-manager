package com.miqueiasfb.task_manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miqueiasfb.task_manager.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  User findByEmail(String email);

}