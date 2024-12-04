package com.miqueiasfb.task_manager.entities;

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Title is mandatory")
  @Size(max = 255, message = "Only 255 characters are allowed")
  private String title;

  @NotBlank(message = "Description is mandatory")
  private String description;

  @NotNull(message = "Priority is mandatory")
  @Min(value = 1, message = "Priority must be greater than 0")
  @Max(value = 5, message = "Priority must be less than 6")
  private int priority;

  private boolean done = false;
  private LocalDate doneAt;
  private LocalDate createdAt = LocalDate.now();

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
